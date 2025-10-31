package dev.afecioru.springbatch.domain;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.transaction.PlatformTransactionManager;


public abstract class Task implements Tasklet {
  protected abstract String getTaskName();
  protected abstract JobRepository getJobRepository();
  protected abstract PlatformTransactionManager getTransactionManager();

  public Step step() {
    return new StepBuilder(getTaskName(),getJobRepository())
      .tasklet(this, getTransactionManager())
      .build();
  }
}
