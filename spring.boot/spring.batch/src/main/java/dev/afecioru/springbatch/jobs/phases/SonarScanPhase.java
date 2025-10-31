package dev.afecioru.springbatch.jobs.phases;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.tasks.sonar.SonarCreateProjectTask;
import dev.afecioru.springbatch.jobs.tasks.sonar.SonarScanTask;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@RequiredArgsConstructor
public class SonarScanPhase {
  private final static String PHASE_NAME = "sonar-scan";

  private final CodeRepo codeRepo;
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;
  private final TracingStepListener tracingStepListener;

  public Flow flow() {
    val sonarScanStep = new SonarScanTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val sonarCreateProjectStep = new SonarCreateProjectTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();

    return new FlowBuilder<SimpleFlow>(PHASE_NAME)
      .start(sonarCreateProjectStep)
      .next(sonarScanStep)
      .build();
  }
}
