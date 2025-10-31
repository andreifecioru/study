package dev.afecioru.springbatch.jobs.phases;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.tasks.publish.PublishArtifactsTask;
import dev.afecioru.springbatch.jobs.tasks.publish.PublishLogsTask;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@RequiredArgsConstructor
public class PublishPhase {
  private final static String PHASE_NAME = "publish-phase";

  private final CodeRepo codeRepo;
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final TracingStepListener tracingStepListener;

  public Flow flow() {
    val publishArtifactsStep = new PublishArtifactsTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val publishLogsStep = new PublishLogsTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();

    return new FlowBuilder<SimpleFlow>(PHASE_NAME)
      .start(publishArtifactsStep)
      .next(publishLogsStep)
      .build();
  }
}
