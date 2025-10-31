package dev.afecioru.springbatch.jobs;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.phases.*;
import dev.afecioru.springbatch.tracing.TracingJobListener;
import dev.afecioru.springbatch.tracing.TracingRegistry;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.val;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


@SuppressWarnings("DuplicatedCode")
@Component
@RequiredArgsConstructor
public class BuildJob {
  private final static String JOB_NAME = "build-job";

  private final JobRepository jobRepository;
  private final TaskExecutor taskExecutor;
  private final PlatformTransactionManager transactionManager;
  private final TracingJobListener tracingJobListener;
  private final TracingStepListener tracingStepListener;
  private final Tracer tracer;
  private final TracingRegistry tracingRegistry;

  private final PreparePhase preparePhase;
  private final BuildPhase buildPhase;
  private final TestRunPhase testRunPhase;
  private final SonarScanPhase sonarScanPhase;
  private final OakScanPhase oakScanPhase;
  private final PublishPhase publishPhase;

  public Job buildSequential(CodeRepo codeRepo) {
    val prepareFlow = preparePhase.flow(codeRepo);
    val buildFlow = buildPhase.flow(codeRepo);
    val testFlow = testRunPhase.flow(codeRepo);
    val sonarFlow = sonarScanPhase.flow(codeRepo);
    val oakFlow = oakScanPhase.flow(codeRepo);
    val publishFlow = publishPhase.flow(codeRepo);

    return new JobBuilder(JOB_NAME, jobRepository)
      .listener(tracingJobListener)
      .start(prepareFlow)
      .next(buildFlow)
      .next(testFlow)
      .next(sonarFlow)
      .next(oakFlow)
      .next(publishFlow)
      .end()
      .build();
  }

  public Job build(CodeRepo codeRepo) {
    val prepareFlow = preparePhase.flow(codeRepo);
    val buildFlow = buildPhase.flow(codeRepo);
    val testFlow = testRunPhase.flow(codeRepo);
    val sonarFlow = sonarScanPhase.flow(codeRepo);
    val oakFlow = oakScanPhase.flow(codeRepo);
    val publishFlow = publishPhase.flow(codeRepo);

    Flow qualityCheckFlow = new FlowBuilder<SimpleFlow>("fanOut")
      .split(taskExecutor)
      .add(testFlow, sonarFlow, oakFlow)
      .build();

    return new JobBuilder(JOB_NAME, jobRepository)
      .listener(tracingJobListener)
      .start(prepareFlow)
      .next(buildFlow)
      .on("*").to(qualityCheckFlow)
      .next(publishFlow)
      .end()
      .build();
  }
}
