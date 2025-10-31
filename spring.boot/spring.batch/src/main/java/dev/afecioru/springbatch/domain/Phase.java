package dev.afecioru.springbatch.domain;

import dev.afecioru.springbatch.domain.models.CodeRepo;
import dev.afecioru.springbatch.tracing.TracingRegistry;
import dev.afecioru.springbatch.tracing.TracingStepListener;
import io.opentracing.Span;
import io.opentracing.Tracer;
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

import static dev.afecioru.springbatch.tracing.TracingRegistry.JOB_SPAN_KEY;
import static dev.afecioru.springbatch.tracing.TracingRegistry.PHASE_SPAN_KEY;


@Slf4j
@RequiredArgsConstructor
public abstract class Phase {

  protected abstract JobRepository getJobRepository();
  protected abstract PlatformTransactionManager getTransactionManager();
  protected abstract TracingStepListener getTracingStepListener();
  protected abstract Tracer getTracer();
  protected abstract TracingRegistry getTracingRegistry();

  protected abstract String getPhaseName();
  protected abstract Flow buildPhaseFlow(CodeRepo codeRepo);

  public Flow flow(CodeRepo codeRepo) {
    Flow phaseFlow = buildPhaseFlow(codeRepo);

    Step startPhaseStep = createPhaseStartStep();
    Step endPhaseStep = createPhaseEndStep();

    return new FlowBuilder<SimpleFlow>("phase-" + getPhaseName())
      .start(startPhaseStep)
      .next(phaseFlow)
      .next(endPhaseStep)
      .build();
  }

  private Step createPhaseStartStep() {
    return new StepBuilder("phase-start-" + getPhaseName(), getJobRepository())
      .tasklet(new PhaseStartTasklet(), getTransactionManager())
      .listener(getTracingStepListener())
      .build();
  }

  private Step createPhaseEndStep() {
    return new StepBuilder("phase-end-" + getPhaseName(), getJobRepository())
      .tasklet(new PhaseEndTasklet(), getTransactionManager())
      .listener(getTracingStepListener())
      .build();
  }

  private class PhaseStartTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
      // Get job span from execution context (works in parallel flows)
      String jobSpanKey = (String) chunkContext.getStepContext().getJobExecutionContext().get(JOB_SPAN_KEY);
      Span jobSpan = getTracingRegistry().getSpan(jobSpanKey);

      Span phaseSpan = getTracer().buildSpan("phase: " + getPhaseName())
        .withTag("phase.name", getPhaseName())
        .withTag("span.kind", "phase")
        .asChildOf(jobSpan)
        .start();

      String phaseSpanKey = PHASE_SPAN_KEY + "." + getPhaseName() + "-" + System.currentTimeMillis();
      getTracingRegistry().registerSpan(phaseSpanKey, phaseSpan);

      String contextKey = PHASE_SPAN_KEY + "." + getPhaseName();
      contribution.getStepExecution().getJobExecution().getExecutionContext().put(contextKey, phaseSpanKey);

      log.info("Started phase: {} - stored span key: {} under context key: {}", getPhaseName(), phaseSpanKey, contextKey);
      return RepeatStatus.FINISHED;
    }
  }

  private class PhaseEndTasklet implements Tasklet {
    @Override
    public RepeatStatus execute(StepContribution contribution, ChunkContext chunkContext) {
      String phaseSpanKey = (String) contribution.getStepExecution().getJobExecution().getExecutionContext().get(PHASE_SPAN_KEY + "." + getPhaseName());

      if (phaseSpanKey == null) {
        log.warn("No phase span key found for phase: {}", getPhaseName());
        return RepeatStatus.FINISHED;
      }

      Span phaseSpan = getTracingRegistry().getSpan(phaseSpanKey);

      phaseSpan.setTag("phase.status", "COMPLETED");
      phaseSpan.finish();
      getTracingRegistry().removeSpan(phaseSpanKey);
      log.info("Finished phase: {}", getPhaseName());

      return RepeatStatus.FINISHED;
    }
  }
}