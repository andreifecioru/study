import requests

# stubs are the type definition files for 3rd party libs
#   pip install -U types-requests


def main() -> None:
  resp = requests.get("https://google.com", timeout=5)
  status: int = resp.status_code
  print(f"Status code: {status}")
  # status = 'ok' # after we install stubs for the requests lib, this will be flagged


if __name__ == "__main__":
  main()
