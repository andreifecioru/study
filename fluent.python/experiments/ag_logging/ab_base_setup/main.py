import atexit
import logging
import logging.config
import time
from logging.handlers import QueueListener
from pathlib import Path
from typing import Any, cast

import yaml

LOGGING_CONF_FILE = Path(__file__).parent / "config" / "base.yaml"

logger = logging.getLogger("my_app")


def setup_logging(config_path: Path) -> None:
  file_content = config_path.read_text(encoding="utf-8")
  config_data = yaml.safe_load(file_content)
  if not isinstance(config_data, dict):
    err_msg = f"Logging configuration at {config_path!s} could not be loaded"
    raise TypeError(err_msg)
  logging.config.dictConfig(config=cast("dict[str, Any]", config_data))

  # start the off-thread logging
  queue_handler = logging.getHandlerByName("queue_handler")
  if queue_handler is not None:
    listener = cast("QueueListener | None", getattr(queue_handler, "listener", None))
    if listener is not None:
      listener.start()
      atexit.register(listener.stop)


def do_work() -> None:
  warn_threshold = 5
  error_threshold = 8

  for i in range(10):
    if i >= error_threshold:
      logger.error("That's enough work!")
    elif i >= warn_threshold:
      # JSON formatter allows us to pass in extra info
      logger.warning("That's a lot of work!", extra={"iteration": i})
    else:
      logger.info("Doing some work")

    time.sleep(0.5)


def main() -> None:
  setup_logging(LOGGING_CONF_FILE)
  do_work()


if __name__ == "__main__":
  main()
