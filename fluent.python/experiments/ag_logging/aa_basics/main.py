import logging

logger = logging.getLogger("my_app")


def main() -> None:
  logging.basicConfig(level="DEBUG")

  try:
    print(1 / 0)
  except ZeroDivisionError:
    logger.exception("the application crashed")


if __name__ == "__main__":
  main()
