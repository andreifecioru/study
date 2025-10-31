package dev.afecioru.springbatch.tracing;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TracingStepListener implements StepExecutionListener {

  private final Tracer tracer;
  private static final ThreadLocal<Span> stepSpanHolder = new ThreadLocal<>();

  @Override
  public void beforeStep(StepExecution stepExecution) {
    Span jobSpan = TracingJobListener.getCurrentJobSpan();

    Span stepSpan = tracer.buildSpan("batch-step: " + stepExecution.getStepName())
      .withTag("step.name", stepExecution.getStepName())
      .withTag("step.id", String.valueOf(stepExecution.getId()))
      .asChildOf(jobSpan)
      .start();

    stepSpanHolder.set(stepSpan);
    log.info("Started tracing for step: {}", stepExecution.getStepName());
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    Span stepSpan = stepSpanHolder.get();
    if (stepSpan != null) {
      stepSpan.setTag("step.status", stepExecution.getStatus().toString());
      stepSpan.setTag("step.exit.code", stepExecution.getExitStatus().getExitCode());
      stepSpan.setTag("step.read.count", String.valueOf(stepExecution.getReadCount()));
      stepSpan.setTag("step.write.count", String.valueOf(stepExecution.getWriteCount()));
      stepSpan.finish();
      stepSpanHolder.remove();
      log.info("Finished tracing for step: {}", stepExecution.getStepName());
    }
    return null;
  }

  public static Span getCurrentStepSpan() {
    return stepSpanHolder.get();
  }
}