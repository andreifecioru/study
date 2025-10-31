package dev.afecioru.springbatch.domain;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import io.opentracing.Span;
import io.opentracing.Tracer;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.StepContribution;
import org.springframework.batch.core.job.builder.FlowBuilder;
import org.springframework.batch.core.job.flow.Flow;
import org.springframework.batch.core.job.flow.support.SimpleFlow;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.scope.context.ChunkContext;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.core.step.tasklet.Tasklet;
import org.springframework.batch.repeat.RepeatStatus;
import org.springframework.transaction.PlatformTransactionManager;


@Slf4j
@RequiredArgsConstructor
public abstract class Phase {

  protected abstract CodeRepo getCodeRepo();
  protected abstract JobRepository getJobRepository();
  protected abstract PlatformTransactionManager getTransactionManager();
  protected abstract TracingStepListener getTracingStepListener();
  protected abstract Tracer getTracer();

  protected abstract String getPhaseName();
  protected abstract Flow buildPhaseFlow();

  public Flow flow() {
    Flow phaseFlow = buildPhaseFlow();

    Step startPhaseStep = createPhaseStartStep();
    Step endPhaseStep = createPhaseEndStep();

    return new FlowBuilder<SimpleFlow>("traced-" + getPhaseName())
      .start(startPhaseStep)
      .next(phaseFlow)
      .next(endPhaseStep)
      .build();
  }

  private Step createPhaseStartStep() {
    return new StepBuilder("start-" + getPhaseName(), getJobRepository())
      .tasklet(new PhaseStartTasklet(), getTransactionManager())
      .listener(getTracingStepListener())
      .build();
  }

  private Step createPhaseEndStep() {
    return new StepBuilder("end-" + getPhaseName(), getJobRepository())
      .tasklet(new PhaseEndTasklet(), getTransactionManager())
      .listener(getTracingStepListener())
      .build();
  }

  private class PhaseStartTasklet implements Tasklet {
    private static final String PHASE_SPAN_KEY = "phase.span.";
    private static final String JOB_SPAN_KEY = "job.span";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
      Span jobSpan = (Span) chunkContext.getStepContext().getJobExecutionContext().get(JOB_SPAN_KEY);

      Span phaseSpan = getTracer().buildSpan("batch-phase: " + getPhaseName())
        .withTag("phase.name", getPhaseName())
        .asChildOf(jobSpan)
        .start();

      chunkContext.getStepContext().getJobExecutionContext().put(PHASE_SPAN_KEY + getPhaseName(), phaseSpan);
      log.info("Started phase: {}", getPhaseName());

      return RepeatStatus.FINISHED;
    }
  }

  private class PhaseEndTasklet implements Tasklet {
    private static final String PHASE_SPAN_KEY = "phase.span.";

    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
      Span phaseSpan = (Span) chunkContext.getStepContext().getJobExecutionContext().get(PHASE_SPAN_KEY + getPhaseName());

      if (phaseSpan != null) {
        phaseSpan.setTag("phase.status", "COMPLETED");
        phaseSpan.finish();
        log.info("Finished phase: {}", getPhaseName());
      }

      return RepeatStatus.FINISHED;
    }
  }
}