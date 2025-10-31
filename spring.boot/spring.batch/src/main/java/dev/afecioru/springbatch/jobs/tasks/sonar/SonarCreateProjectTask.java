package dev.afecioru.springbatch.jobs.tasks.sonar;

import dev.afecioru.springbatch.domain.Task;
import dev.afecioru.springbatch.domain.models.CodeRepo;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.PlatformTransactionManager;

import java.time.Duration;


@Slf4j
@RequiredArgsConstructor
public class SonarCreateProjectTask extends Task {
  private static final String TASKLET_NAME = "sonar-create-project";
  private static final Duration SLEEP_SECONDS = Duration.ofSeconds(4);

  private final CodeRepo codeRepo;

  @Getter
  private final JobRepository jobRepository;
  @Getter
  private final PlatformTransactionManager transactionManager;

  @Override
  public String getTaskName() { return TASKLET_NAME; }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

    log.info("[{}] Creating Sonar scan for repo {}", TASKLET_NAME, codeRepo.name());
    Thread.sleep(SLEEP_SECONDS.toMillis());
    log.info("[{}] Done.", TASKLET_NAME);

    return RepeatStatus.FINISHED;
  }
}
