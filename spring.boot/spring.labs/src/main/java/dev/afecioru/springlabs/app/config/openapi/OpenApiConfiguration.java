package dev.afecioru.springlabs.app.config.openapi;

import io.swagger.v3.jaxrs2.integration.JaxrsOpenApiContextBuilder;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.servers.Server;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.integration.OpenApiConfigurationException;
import io.swagger.v3.oas.integration.SwaggerConfiguration;
import org.springframework.context.annotation.Configuration;

import jakarta.annotation.PostConstruct;
import java.util.Set;

@Configuration
@OpenAPIDefinition(
    info = @Info(
        title = "JAX-RS ToDo API",
        description = "JAX-RS implementation of ToDo API using Jersey",
        version = "2.0.0"
    ),
    servers = {
        @Server(url = "http://localhost:8080/api/v2", description = "Local JAX-RS server")
    },
    tags = {
        @Tag(name = "ToDo Resource", description = "Operations for managing ToDo items")
    }
)
public class OpenApiConfiguration {

    @PostConstruct
    public void configureOpenApi() throws OpenApiConfigurationException {
        SwaggerConfiguration config = new SwaggerConfiguration()
                .resourcePackages(Set.of("dev.afecioru.springlabs.app.api.v2.resources"))
                .ignoredRoutes(Set.of("/openapi.json", "/openapi.yaml", "/openapi"))
                .prettyPrint(true);

            new JaxrsOpenApiContextBuilder<>()
                .openApiConfiguration(config)
                .buildContext(true);
    }
}
