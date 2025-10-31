package dev.afecioru.springbatch.tracing;

import io.opentracing.Span;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;


@Slf4j
@Component
public class TracingRegistry {
  public static final String JOB_SPAN_KEY = "job.span.key";
  public static final String PHASE_SPAN_KEY = "phase.span.key";
  public static final String TASK_SPAN_KEY = "task.span.key";

  private final ConcurrentMap<String, Span> spans = new ConcurrentHashMap<>();

  public void registerSpan(String key, Span span) {
    spans.put(key, span);
    log.debug("Registered span: {} -> {}", key, span);
  }

  public Span getSpan(String key) {
    Span span = spans.get(key);
    if (span == null) {
      throw new IllegalStateException("Span not found for key: " + key);
    }
    log.debug("Retrieved span: {} -> {}", key, span);
    return span;
  }

  public void removeSpan(String key) {
    Span span = spans.remove(key);
    if (span == null) {
      log.warn("Attempted to remove non-existent span: {}", key);
    } else {
      log.debug("Removed span: {} -> {}", key, span);
    }
  }

  public void clear() {
    spans.clear();
    log.debug("Cleared all spans");
  }
}