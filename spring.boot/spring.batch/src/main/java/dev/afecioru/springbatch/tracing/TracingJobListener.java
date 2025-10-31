package dev.afecioru.springbatch.tracing;

import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class TracingJobListener implements JobExecutionListener {

  private final Tracer tracer;
  private static final String JOB_SPAN_KEY = "job.span";
  private static final ThreadLocal<Span> jobSpanHolder = new ThreadLocal<>();

  @Override
  public void beforeJob(JobExecution jobExecution) {
    String jobName = jobExecution.getJobInstance().getJobName();
    Span jobSpan = tracer.buildSpan("batch-job: " + jobName)
      .withTag("job.name", jobName)
      .withTag("job.id", String.valueOf(jobExecution.getJobId()))
      .start();

    jobSpanHolder.set(jobSpan);
    // Store span context as string instead of span object
    jobExecution.getExecutionContext().putString(JOB_SPAN_KEY, jobSpan.context().toString());
    log.info("Started tracing for job: {}", jobName);
  }

  @Override
  public void afterJob(JobExecution jobExecution) {
    Span jobSpan = jobSpanHolder.get();
    if (jobSpan != null) {
      jobSpan.setTag("job.status", jobExecution.getStatus().toString());
      jobSpan.setTag("job.exit.code", jobExecution.getExitStatus().getExitCode());
      jobSpan.finish();
      jobSpanHolder.remove();

      log.info("Finished tracing for job: {}", jobExecution.getJobInstance().getJobName());
    }
  }

  public static Span getCurrentJobSpan() {
    return jobSpanHolder.get();
  }
}