package dev.afecioru.springbatch.jobs.tasks.maven;

import dev.afecioru.springbatch.domain.Task;
import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.tracing.TracingStepListener;
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
public class MavenSetVersionTask extends Task  {
  private static final String TASKLET_NAME = "mvn-set-version";
  private static final Duration SLEEP_SECONDS = Duration.ofSeconds(1);

  private final CodeRepo codeRepo;

  @Getter
  private final JobRepository jobRepository;
  @Getter
  private final PlatformTransactionManager transactionManager;
  @Getter
  private final TracingStepListener tracingStepListener;

  @Override
  public String getTaskName() { return TASKLET_NAME; }

  @Override
  public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) throws Exception {

    log.info("[{}] Setting Maven version...", TASKLET_NAME);
    Thread.sleep(SLEEP_SECONDS.toMillis());
    log.info("[{}] Done.", TASKLET_NAME);

    return RepeatStatus.FINISHED;
  }
}
