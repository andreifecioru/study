import yfinance as yf

from rich.console import Console
from rich.theme import Theme
from datetime import  datetime, UTC

tool_theme = Theme({"tool": "green bold"})
console = Console(theme=tool_theme)


def get_stock_price(ticker: str) -> dict:
  """
  Retrieves stock price for a given ticker symbol.

  :param ticker: The stock ticker
  :type ticker: str
  :return: The stock price information as a dictionary
  """
  console.print(f"[tool](TOOL)[/] Retrieving stock price for {ticker}")

  try:
    stock_info = yf.Ticker(ticker).info
    current_price = stock_info.get("currentPrice")
    if current_price is None:
      return {
        "status": "error",
        "message": f"Could not retrieve stock price for {ticker}",
      }
  except Exception as e:
    return {
      "status": "error",
      "message": f"Failed to retrieve stock info for {ticker}. ({e})",
    }
  else:
    return {
      "status": "success",
      "ticker": ticker,
      "price": current_price,
      "message": f"The current price for {ticker} is {current_price}",
      "timestamp": datetime.now(tz=UTC).isoformat()
    }
