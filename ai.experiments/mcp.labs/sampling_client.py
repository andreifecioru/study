import asyncio
import os
from mcp import ClientSession, StdioServerParameters, types
from mcp.client.stdio import stdio_client
from mcp.shared.context import RequestContext

# Create server parameters for stdio connection
server_params = StdioServerParameters(
    command="python",
    args=["sampling_example.py"],
    env=os.environ.copy()
)

# Sampling callback - handles requests from server for LLM completions
async def handle_sampling_message(
    context: RequestContext[ClientSession, None], 
    params: types.CreateMessageRequestParams
) -> types.CreateMessageResult:
    """Handle sampling requests from the MCP server."""
    print(f"Server requested sampling with {len(params.messages)} messages")
    
    # In a real implementation, you would:
    # 1. Extract the prompt from params.messages
    # 2. Send it to your LLM (OpenAI, local model, etc.)
    # 3. Return the generated response
    
    # Extract the user message
    if params.messages and hasattr(params.messages[0], 'content'):
        if hasattr(params.messages[0].content, 'text'):
            user_message = params.messages[0].content.text
        else:
            user_message = str(params.messages[0].content)
    else:
        user_message = "No message provided"
    
    print(f"Processing request: {user_message}")
    
    # Simulate different responses based on the request type
    if "poem" in user_message.lower():
        simulated_response = """Mountains tall and proud they stand,
Reaching up to touch the sky,
Silent guardians of the land,
Where eagles soar and spirits fly."""
    elif "analyze" in user_message.lower():
        simulated_response = "The data shows a positive growth trend with Q1 at 100, Q2 at 150 (50% increase), and Q3 at 200 (33% increase). This indicates strong business performance with accelerating growth."
    elif "summary" in user_message.lower() or "summarize" in user_message.lower():
        simulated_response = "This is a concise summary of the provided text, highlighting the key points and main ideas."
    elif "translate" in user_message.lower():
        simulated_response = "Bonjour le monde!" if "french" in user_message.lower() else "Hola mundo!"
    else:
        simulated_response = f"AI-generated response for: {user_message}"
    
    return types.CreateMessageResult(
        role="assistant",
        content=types.TextContent(
            type="text",
            text=simulated_response,
        ),
        model="gpt-4",  # Specify which model was used
        stopReason="endTurn",
    )

async def run():
    """Run the sampling client example."""
    async with stdio_client(server_params) as (read, write):
        # Create session with sampling callback
        async with ClientSession(
            read, 
            write, 
            sampling_callback=handle_sampling_message
        ) as session:
            # Initialize the connection
            await session.initialize()

            # List available tools
            tools = await session.list_tools()
            print(f"Available tools: {[t.name for t in tools.tools]}")

            print("\n=== Testing Sampling Tools ===")
            
            # Test poem generation
            print("\n1. Generating a poem about mountains...")
            result = await session.call_tool("generate_poem", arguments={"topic": "mountains"})
            if result.content and len(result.content) > 0:
                print(f"Poem result: {result.content[0].text}")
            
            # Test data analysis
            print("\n2. Analyzing sales data...")
            result = await session.call_tool("analyze_data", arguments={"data": "Sales: Q1=100, Q2=150, Q3=200"})
            if result.content and len(result.content) > 0:
                print(f"Analysis result: {result.content[0].text}")
            
            # Test text summarization
            print("\n3. Summarizing text...")
            long_text = "Artificial intelligence is transforming industries across the globe. From healthcare to finance, AI is enabling new capabilities and improving efficiency. Machine learning algorithms can process vast amounts of data to identify patterns and make predictions."
            result = await session.call_tool("summarize_text", arguments={"text": long_text})
            if result.content and len(result.content) > 0:
                print(f"Summary result: {result.content[0].text}")
            
            # Test translation
            print("\n4. Translating text to French...")
            result = await session.call_tool("translate_text", arguments={"text": "Hello world!", "target_language": "French"})
            if result.content and len(result.content) > 0:
                print(f"Translation result: {result.content[0].text}")

if __name__ == "__main__":
    asyncio.run(run())
