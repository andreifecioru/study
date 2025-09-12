package dev.afecioru.springlabs.app.config.jersey;

import io.swagger.v3.jaxrs2.integration.resources.OpenApiResource;
import jakarta.ws.rs.ApplicationPath;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
@ApplicationPath("/api/v2")
public class JerseyConfig extends ResourceConfig {
    
    public JerseyConfig() {
        // Register JAX-RS resources
        packages("dev.afecioru.springlabs.app.api.v2.resources");

        // Register Jackson for JSON processing
        packages("org.glassfish.jersey.jackson");

        // Register OpenAPI resource for JAX-RS documentation
        register(OpenApiResource.class);

        // Configure OpenAPI scanning
        property("swagger.config.id", "jax-rs-api");
        property("swagger.pretty.print", "true");

        // Only scan specific packages to avoid including OpenAPI endpoints
        property("swagger.scanner.packages", "dev.afecioru.springlabs.app.api.v2.resources");

        // Exclude OpenAPI resource itself from documentation
        property("swagger.scanner.exclude.packages", "io.swagger.v3.jaxrs2.integration.resources");

        // Additional exclusions
        property("swagger.scanner.exclude.classes", "io.swagger.v3.jaxrs2.integration.resources.OpenApiResource");
        property("swagger.scanner.exclude.paths", "/openapi.json,/openapi.yaml,/openapi,/swagger-ui,/application.wadl");

        // Exclude WADL endpoints specifically
        property("swagger.scanner.ignore.routes", "/application.wadl,/application.wadl/{path}");
    }
}
