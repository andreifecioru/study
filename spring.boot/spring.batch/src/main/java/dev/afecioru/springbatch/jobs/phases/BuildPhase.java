package dev.afecioru.springbatch.jobs.phases;

import dev.afecioru.springbatch.domain.Phase;
import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.tasks.maven.*;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import lombok.Getter;
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
public class BuildPhase {
  private final static String PHASE_NAME = "build-phase";

  @Getter
  private final CodeRepo codeRepo;
  @Getter
  private final JobRepository jobRepository;
  @Getter
  private final PlatformTransactionManager transactionManager;
  @Getter
  private final TracingStepListener tracingStepListener;

  public Flow flow() {
    val mvnSetVersionStep = new MavenSetVersionTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val mvnResolvePluginsStep = new MavenResolvePluginsTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val mvnInstallCacheExtensionStep = new MavenInstallCacheExntensionTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val mvnResolveDependenciesStep = new MavenResolveDependenciesTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();
    val mvnPackageStep = new MavenPackageTask(codeRepo, jobRepository, transactionManager, tracingStepListener).step();

    return new FlowBuilder<SimpleFlow>(PHASE_NAME)
      .start(mvnSetVersionStep)
      .next(mvnResolvePluginsStep)
      .next(mvnInstallCacheExtensionStep)
      .next(mvnResolveDependenciesStep)
      .next(mvnPackageStep)
      .build();
  }
}
