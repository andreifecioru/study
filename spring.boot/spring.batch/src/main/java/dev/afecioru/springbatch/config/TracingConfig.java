package dev.afecioru.springbatch.config;

import io.jaegertracing.Configuration;
import io.opentracing.Tracer;
import org.springframework.context.annotation.Bean;

@org.springframework.context.annotation.Configuration
public class TracingConfig {

  @Bean
  public Tracer tracer() {
    Configuration.SamplerConfiguration samplerConfig = Configuration.SamplerConfiguration.fromEnv()
      .withType("const")
      .withParam(1);

    Configuration.ReporterConfiguration reporterConfig = Configuration.ReporterConfiguration.fromEnv()
      .withLogSpans(true)
      .withSender(
        Configuration.SenderConfiguration.fromEnv()
          .withEndpoint("http://localhost:14268/api/traces")
      );

    Configuration config = new Configuration("spring-batch-app")
      .withSampler(samplerConfig)
      .withReporter(reporterConfig);

    return config.getTracer();
  }
}