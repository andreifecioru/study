from fastmcp import FastMCP

mcp = FastMCP(
    name="Calculator",
    instructions="""
        This server provides support for basic math operations.
        It can add, subtract, multiply, and divide.
    """
)

def str_to_num(s: str) -> int | float:
    try:
        return int(s)
    except ValueError:
        return float(s)

@mcp.tool(
    name="Addition",
    title="Add two numbers.",
    description="Add two numbers together.",
    tags={"math", "addition"}
)
def add(a: int | float | str, b: int | float | str) -> int | float:
    """
    Add two numbers together.

    :param a: left operand
    :param b: right operand

    :return: Sum of a and b
    """
    _a = str_to_num(a)
    _b = str_to_num(b)

    print("Adding", _a, "and", _b)
    return _a + _b


@mcp.tool(
    name="Subtraction",
    title="Subtract two numbers.",
    description="Subtract one number from another.",
    tags={"math", "subtraction"}
)
def subtract(a: int | float | str, b: int | float | str) -> int | float:
    """
    Subtract one number from another.

    :param a: left operand
    :param b: right operand

    :return: Subtraction of b from a.
    """
    _a = str_to_num(a)
    _b = str_to_num(b)

    print("Subtracting", _b, "from", _a)
    return _a - _b


@mcp.tool(
    name="Multiplication",
    title="Multiply two numbers.",
    description="Multiply two numbers together.",
    tags={"math", "multiplication"}
)
def multiply(a: int | float | str, b: int | float | str) -> int | float:
    """
    Multiply two numbers together.

    :param a: left operand
    :param b: right operand

    :return: Multiplication of a and b.
    """
    _a = str_to_num(a)
    _b = str_to_num(b)

    print("Multiplying", _a, "and", _b)
    return _a * _b


@mcp.tool(
    name="Division",
    title="Divide two numbers.",
    description="Divide one number with another.",
    tags={"math", "division"}
)
def divide(a: int | float | str, b: int | float | str) -> int | float:
    """
    Divide one number with another.

    :param a: left operand
    :param b: right operand

    :return: Division of a by b.
    """
    _a = str_to_num(a)
    _b = str_to_num(b)

    print("Dividing", _a, "by", _b)
    return _a / _b


if __name__ == '__main__':
    mcp.run(
        transport="http",
        host="127.0.0.1",
        port=8000,
    )

