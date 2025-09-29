from fastmcp import FastMCP, Context
from mcp.types import SamplingMessage, TextContent

mcp = FastMCP(name="Sampling Example")

@mcp.tool()
async def generate_poem(topic: str, ctx: Context) -> str:
    """Generate a poem using LLM sampling."""
    prompt = f"Write a short poem about {topic}"

    result = await ctx.session.create_message(
        messages=[
            SamplingMessage(
                role="user",
                content=TextContent(type="text", text=prompt),
            )
        ],
        max_tokens=100,
    )

    if result.content.type == "text":
        return result.content.text
    return str(result.content)

@mcp.tool()
async def analyze_data(data: str, ctx: Context) -> str:
    """Analyze data using LLM sampling."""
    prompt = f"Analyze this data and provide insights: {data}"
    
    result = await ctx.session.create_message(
        messages=[
            SamplingMessage(
                role="user", 
                content=TextContent(type="text", text=prompt)
            )
        ],
        max_tokens=200,
    )
    
    if result.content.type == "text":
        return f"Analysis: {result.content.text}"
    return "Analysis could not be completed"

@mcp.tool()
async def summarize_text(text: str, ctx: Context) -> str:
    """Summarize text using LLM sampling."""
    prompt = f"Please provide a concise summary of the following text: {text}"
    
    result = await ctx.session.create_message(
        messages=[
            SamplingMessage(
                role="user",
                content=TextContent(type="text", text=prompt)
            )
        ],
        max_tokens=150,
    )
    
    if result.content.type == "text":
        return f"Summary: {result.content.text}"
    return "Summary could not be generated"

@mcp.tool()
async def translate_text(text: str, target_language: str, ctx: Context) -> str:
    """Translate text using LLM sampling."""
    prompt = f"Translate the following text to {target_language}: {text}"
    
    result = await ctx.session.create_message(
        messages=[
            SamplingMessage(
                role="user",
                content=TextContent(type="text", text=prompt)
            )
        ],
        max_tokens=200,
    )
    
    if result.content.type == "text":
        return f"Translation: {result.content.text}"
    return "Translation could not be completed"

if __name__ == "__main__":
    mcp.run()
