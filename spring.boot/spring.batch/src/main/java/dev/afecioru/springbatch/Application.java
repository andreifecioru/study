package dev.afecioru.springbatch;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.jobs.BuildJob;
import io.opentracing.Tracer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;


@SpringBootApplication
@Slf4j
@RequiredArgsConstructor
public class Application implements ApplicationRunner {
  private final JobLauncher jobLauncher;
  private final BuildJob buildJob;
  private final ApplicationContext applicationContext;

  public static void main(String[] args) {
    SpringApplication.run(Application.class, args);
  }

  @Override
  public void run(ApplicationArguments args) throws Exception {
    // Debug: Check if Tracer bean exists
    try {
      Tracer tracer = applicationContext.getBean(Tracer.class);
      log.info("Tracer bean found: {}", tracer.getClass().getName());
    } catch (Exception e) {
      log.error("Tracer bean not found: {}", e.getMessage());
    }

    val codeRepo = new CodeRepo(
      "spring.batch",
      "https://github.com/afecioru/spring.batch.git");

    jobLauncher.run(buildJob.build(codeRepo), new JobParameters());
  }
}
