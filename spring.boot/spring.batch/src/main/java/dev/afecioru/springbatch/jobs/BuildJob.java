package dev.afecioru.springbatch.jobs;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.phases.*;
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

  public Job buildSequential(CodeRepo codeRepo) {
    val preparePhase = new PreparePhase(codeRepo, jobRepository, transactionManager).flow();
    val buildPhase = new BuildPhase(codeRepo, jobRepository, transactionManager).flow();
    val testPhase = new TestRunPhase(codeRepo, jobRepository, transactionManager).flow();
    val sonarScanPhase = new SonarScanPhase(codeRepo, jobRepository, transactionManager).flow();
    val oakScanPhase = new OakScanPhase(codeRepo, jobRepository, transactionManager).flow();
    val publishPhase = new PublishPhase(codeRepo, jobRepository, transactionManager).flow();

    return new JobBuilder(JOB_NAME, jobRepository)
      .start(preparePhase)
      .next(buildPhase)
      .next(testPhase)
      .next(sonarScanPhase)
      .next(oakScanPhase)
      .next(publishPhase)
      .end()
      .build();
  }

  public Job build(CodeRepo codeRepo) {
    val preparePhase = new PreparePhase(codeRepo, jobRepository, transactionManager).flow();
    val buildPhase = new BuildPhase(codeRepo, jobRepository, transactionManager).flow();
    val testPhase = new TestRunPhase(codeRepo, jobRepository, transactionManager).flow();
    val sonarScanPhase = new SonarScanPhase(codeRepo, jobRepository, transactionManager).flow();
    val oakScanPhase = new OakScanPhase(codeRepo, jobRepository, transactionManager).flow();
    val publishPhase = new PublishPhase(codeRepo, jobRepository, transactionManager).flow();

    Flow qualityCheckFlow = new FlowBuilder<SimpleFlow>("fanOut")
      .split(taskExecutor)
      .add(testPhase, sonarScanPhase, oakScanPhase)
      .build();

    return new JobBuilder("workflow-one", jobRepository)
      .start(preparePhase)
      .next(buildPhase)
      .on("*").to(qualityCheckFlow)
      .next(publishPhase)
      .end()
      .build();
  }
}
