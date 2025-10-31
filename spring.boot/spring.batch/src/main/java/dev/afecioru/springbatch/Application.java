package dev.afecioru.springbatch;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.BuildJob;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Application implements ApplicationRunner {
  private final JobLauncher jobLauncher;
  private final BuildJob buildJob;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    val codeRepo = new CodeRepo(
      "https://github.com/afecioru/spring.batch.git",
      "spring.batch"
    );

    jobLauncher.run(buildJob.buildSequential(codeRepo), new JobParameters());

    // Exit the application after job completion
    System.exit(0);
  }
}
