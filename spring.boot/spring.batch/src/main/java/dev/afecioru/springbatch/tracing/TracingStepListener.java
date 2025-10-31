package dev.afecioru.springbatch.tracing;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.stereotype.Component;

import static dev.afecioru.springbatch.tracing.TracingRegistry.*;

@Slf4j
@Component
@RequiredArgsConstructor
public class TracingStepListener implements StepExecutionListener {

  private final Tracer tracer;
  private final TracingRegistry tracingRegistry;

  @Override
  public void beforeStep(StepExecution stepExecution) {
    // Try to get phase span key first
    String phaseSpanKey = (String) stepExecution.getExecutionContext().get(PHASE_SPAN_KEY);
    Span parentSpan;

    if (phaseSpanKey != null) {
      parentSpan = tracingRegistry.getSpan(phaseSpanKey);
    } else {
      // Fall back to job span for phase start/end steps
      String jobSpanKey = (String) stepExecution.getJobExecution().getExecutionContext().get(JOB_SPAN_KEY);
      parentSpan = tracingRegistry.getSpan(jobSpanKey);
    }

    if (parentSpan != null) {
      String taskSpanKey = TASK_SPAN_KEY + "." + stepExecution.getStepName() + "-" + stepExecution.getId();

      Span taskSpan = tracer.buildSpan("task: " + stepExecution.getStepName())
        .withTag("task.name", stepExecution.getStepName())
        .withTag("task.id", String.valueOf(stepExecution.getId()))
        .withTag("span.kind", "task")
        .asChildOf(parentSpan)
        .start();

      // Store span in step execution context for cleanup
      tracingRegistry.registerSpan(taskSpanKey, taskSpan);
      stepExecution.getExecutionContext().put(TASK_SPAN_KEY + "." + stepExecution.getStepName(), taskSpanKey);

      log.info("Started tracing for task: {} with parent: {}",
        stepExecution.getStepName(),
        parentSpan.getBaggageItem("span.kind") != null ? "phase" : "job");
    } else {
      log.warn("No parent span found for step: {}", stepExecution.getStepName());
    }
  }

  @Override
  public ExitStatus afterStep(StepExecution stepExecution) {
    String taskSpanKey = (String) stepExecution.getExecutionContext().get(TASK_SPAN_KEY + "." + stepExecution.getStepName());

    if (taskSpanKey != null) {
      Span taskSpan = tracingRegistry.getSpan(taskSpanKey);

      taskSpan.setTag("step.status", stepExecution.getStatus().toString());
      taskSpan.setTag("step.exit.code", stepExecution.getExitStatus().getExitCode());
      taskSpan.setTag("step.read.count", String.valueOf(stepExecution.getReadCount()));
      taskSpan.setTag("step.write.count", String.valueOf(stepExecution.getWriteCount()));
      taskSpan.finish();

      tracingRegistry.removeSpan(taskSpanKey);
      log.info("Finished tracing for step: {}", stepExecution.getStepName());
    }

    return stepExecution.getExitStatus();
  }
}