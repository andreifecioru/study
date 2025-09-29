
from google import genai
from google.genai import types
from google.genai.chats import AsyncChat

import fastmcp


class ChatBot:
    _MODEL_ID = "gemini-2.5-flash"
    _SYSTEM_PROMPT = """You are a helpful AI assistant. You can answer general knowledge questions using your training data, and you also have access to mathematical calculation tools.

Guidelines:
- For general knowledge questions (like geography, history, science), answer directly using your knowledge
- For mathematical calculations, use the available calculator tools
- Only use tools when they are specifically needed for the task
- Be conversational and helpful in all responses"""

    def __init__(self, name: str, api_key: str, mcp_clients: list[fastmcp.Client]):
        self.name: str = name
        self._llm: genai.Client = genai.Client(api_key=api_key)
        self._mcp_clients: list[fastmcp.Client] = mcp_clients
        self._chat: AsyncChat = None

    async def ask(self, question: str) -> str:
        if self._chat is None:
            self._chat = await self._create_chat()

        response = await self._chat.send_message(question)

        text_parts = []
        function_calls = []

        for candidate in response.candidates:
            for part in candidate.content.parts:
                if hasattr(part, 'text') and part.text:
                    text_parts.append(part.text)
                elif hasattr(part, 'function_call') and part.function_call:
                    function_calls.append(part.function_call)
                # thought_signature parts are automatically handled by the SDK

        # If there are function calls, handle them
        if function_calls:
            function_responses = []
            for function_call in function_calls:
                function_result = await self._handle_function_call(function_call)

                function_response = types.Part.from_function_response(
                    name=function_call.name,
                    response={'result': function_result}
                )

                function_responses.append(function_response)

            final_response = await self._chat.send_message(function_responses)
            return final_response.text

        # Return concatenated text parts
        return ''.join(text_parts) if text_parts else "I don't know what to say..."

    async def _handle_function_call(self, function_call: types.FunctionCall):
        # convert the LLM function call into an MCP function call
        tool_name = function_call.name
        arguments = dict(function_call.args) if function_call.args else {}

        # call the MCP tool
        async with self._mcp_clients[0] as mcp_client:
            result = await mcp_client.call_tool(tool_name, arguments)
            return str(result.content[0].text if result.content else result)

    async def _create_chat(self) -> AsyncChat:
        tools: list[types.Tool] = []

        for mcp_client in self._mcp_clients:
            async with mcp_client:
                # fetch the tools exposed by the MCP client
                mcp_tools = await mcp_client.list_tools()

                # convert the mcp_tools into LLM-compatible function calls
                llm_functions = [ChatBot._mcp_toop_to_llm_function(tool) for tool in mcp_tools]
                tools.append(types.Tool(function_declarations=llm_functions))

        return self._llm.aio.chats.create(
            model=ChatBot._MODEL_ID,
            config=types.GenerateContentConfig(
                system_instruction=ChatBot._SYSTEM_PROMPT,
                tools=tools
            )
        )

    @staticmethod
    def _mcp_toop_to_llm_function(tool: types.Tool) -> types.FunctionDeclaration:
        params = {}
        required = []

        if hasattr(tool, 'inputSchema') and tool.inputSchema:
            schema = tool.inputSchema
            if 'properties' in schema:
                for param_name, param_info in schema['properties'].items():
                    params[param_name] = {
                        "type": param_info.get('type', 'string'),
                        "description": param_info.get('description', ''),
                    }

            if 'required' in schema:
                required = schema['required']

        return types.FunctionDeclaration(
            name=tool.name,
            description=tool.description,
            parameters=types.Schema(
                type=types.Type.OBJECT,
                properties=params,
                required=required
            )
        )
