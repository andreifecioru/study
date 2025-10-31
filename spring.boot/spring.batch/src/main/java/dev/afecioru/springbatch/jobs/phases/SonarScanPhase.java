package dev.afecioru.springbatch.jobs.phases;

import dev.afecioru.springbatch.domain.Phase;
import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.tasks.sonar.SonarCreateProjectTask;
import dev.afecioru.springbatch.jobs.tasks.sonar.SonarScanTask;
import dev.afecioru.springbatch.tracing.TracingRegistry;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import io.opentracing.Tracer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@RequiredArgsConstructor
@Component
public class SonarScanPhase extends Phase {
  private final static String PHASE_NAME = "sonar-scan";

  @Getter
  private final JobRepository jobRepository;
  @Getter
  private final PlatformTransactionManager transactionManager;
  @Getter
  private final TracingStepListener tracingStepListener;
  @Getter
  private final Tracer tracer;
  @Getter
  private final TracingRegistry tracingRegistry;

  public String getPhaseName() { return PHASE_NAME; }

  @Override
  protected Flow buildPhaseFlow(CodeRepo codeRepo) {
    val sonarScanStep = new SonarScanTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val sonarCreateProjectStep = new SonarCreateProjectTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();

    return new FlowBuilder<SimpleFlow>(PHASE_NAME)
      .start(sonarCreateProjectStep)
      .next(sonarScanStep)
      .build();
  }
}
