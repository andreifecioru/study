from fastmcp import FastMCP

mcp = FastMCP(
    name="Calculator",
    instructions="""
        This server provides support for basic math operations.
        It can add, subtract, multiply, and divide.
    """
)

@mcp.tool(
    name="Addition",
    title="Add two numbers.",
    description="Add two numbers together.",
    tags={"math", "addition"}
)
def add(a: int | float, b: int | float) -> int | float:
    """
    Add two numbers together.

    :param a: left operand
    :param b: right operand

    :return: Sum of a and b
    """
    return a + b


@mcp.tool(
    name="Subtraction",
    title="Subtract two numbers.",
    description="Subtract one number from another.",
    tags={"math", "subtraction"}
)
def subtract(a: int | float, b: int | float) -> int | float:
    """
    Subtract one number from another.

    :param a: left operand
    :param b: right operand

    :return: Subtraction of b from a.
    """
    return a - b


@mcp.tool(
    name="Multiplication",
    title="Multiply two numbers.",
    description="Multiply two numbers together.",
    tags={"math", "multiplication"}
)
def multiply(a: int | float, b: int | float) -> int | float:
    """
    Multiply two numbers together.

    :param a: left operand
    :param b: right operand

    :return: Multiplication of a and b.
    """
    return a * b


@mcp.tool(
    name="Division",
    title="Divide two numbers.",
    description="Divide one number with another.",
    tags={"math", "division"}
)
def multiply(a: int | float, b: int | float) -> int | float:
    """
    Divide one number with another.

    :param a: left operand
    :param b: right operand

    :return: Division of a by b.
    """
    return a / b


if __name__ == '__main__':
    mcp.run()

