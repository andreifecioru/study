package dev.afecioru.springbatch.jobs.phases;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.tasks.oak.OakScanTask;
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
public class OakScanPhase {
  private final static String PHASE_NAME = "sonar-scan";

  private final CodeRepo codeRepo;
  private final JobRepository jobRepository;
  private final PlatformTransactionManager transactionManager;

  public Flow flow() {
    val oakScanStep = new OakScanTask(codeRepo, jobRepository, transactionManager).step();

    return new FlowBuilder<SimpleFlow>(PHASE_NAME)
      .start(oakScanStep)
      .build();
  }
}
