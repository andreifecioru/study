import datetime as dt
import json
import logging
from typing import override

LOG_RECORD_BUILTIN_ATTRS = {
  "args",
  "asctime",
  "created",
  "exc_info",
  "exc_text",
  "filename",
  "funcName",
  "levelname",
  "levelno",
  "lineno",
  "module",
  "msecs",
  "message",
  "msg",
  "name",
  "pathname",
  "process",
  "processName",
  "relativeCreated",
  "stack_info",
  "thread",
  "threadName",
  "taskName",
}


class JsonLogFormatter(logging.Formatter):
  def __init__(self, *, fmt_keys: dict[str, str] | None = None) -> None:
    super().__init__()
    # proper handling of mutable defaults
    self.fmt_keys = fmt_keys if fmt_keys is not None else {}

  @override
  def format(self, record: logging.LogRecord) -> str:
    log_entry = self._prepare_log_entry(record)
    return json.dumps(log_entry, default=str)

  def _prepare_log_entry(self, record: logging.LogRecord) -> dict[str, str]:
    base_fields = {
      "message": record.getMessage(),
      "timestamp": dt.datetime.fromtimestamp(record.created, tz=dt.UTC).isoformat(),
    }

    # extract the exception information
    if record.exc_info is not None:
      base_fields["exc_info"] = self.formatException(record.exc_info)

    if record.stack_info is not None:
      base_fields["stack_info"] = self.formatStack(record.stack_info)

    # extract the data from the record
    log_entry = {
      key: value
      if (value := base_fields.pop(val, None)) is not None
      else getattr(record, val)
      for key, val in self.fmt_keys.items()
    }
    log_entry.update(base_fields)

    # extract the data for the "extra" attributes
    log_entry.update(
      {
        key: value
        for key, value in record.__dict__.items()
        if key not in LOG_RECORD_BUILTIN_ATTRS
      }
    )

    return log_entry


class NonErrorFilter(logging.Filter):
  @override
  def filter(self, record: logging.LogRecord) -> bool | logging.LogRecord:
    return record.levelno <= logging.INFO
