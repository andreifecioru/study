package dev.afecioru.springbatch.tracing;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

import static dev.afecioru.springbatch.tracing.TracingRegistry.JOB_SPAN_KEY;

@Slf4j
@Component
@RequiredArgsConstructor
public class TracingJobListener implements JobExecutionListener {

  private final Tracer tracer;
  private final TracingRegistry tracingRegistry;

  @Override
  public void beforeJob(JobExecution jobExecution) {
    String jobName = jobExecution.getJobInstance().getJobName();
    String jobSpanKey = JOB_SPAN_KEY + "." + jobExecution.getJobId();

    Span jobSpan = tracer.buildSpan("job: " + jobName)
      .withTag("job.name", jobName)
      .withTag("job.id", String.valueOf(jobExecution.getJobId()))
      .withTag("span.kind", "job")
      .start();

    tracingRegistry.registerSpan(jobSpanKey, jobSpan);

    // Store span in execution context for parallel access
    jobExecution.getExecutionContext().put(JOB_SPAN_KEY, jobSpanKey);
    log.info("Started tracing for job: {}", jobName);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    String jobSpanKey = (String) jobExecution.getExecutionContext().get(JOB_SPAN_KEY);

    if (jobSpanKey != null) {
      Span jobSpan = tracingRegistry.getSpan(jobSpanKey);
      jobSpan.setTag("job.status", jobExecution.getStatus().toString());
      jobSpan.setTag("job.exit.code", jobExecution.getExitStatus().getExitCode());
      jobSpan.finish();
      tracingRegistry.removeSpan(jobSpanKey);
      log.info("Finished tracing for job: {}", jobExecution.getJobInstance().getJobName());
    }
  }
}