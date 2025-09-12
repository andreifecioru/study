package dev.afecioru.springlabs.app.api.v2.resources;

import io.swagger.v3.oas.annotations.Hidden;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.core.Context;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.UriInfo;
import org.springframework.stereotype.Component;

import java.net.URI;

@Path("/swagger-ui")
@Component
@Hidden
public class SwaggerUIResource {
    @GET
    public Response swaggerUI(@Context UriInfo uriInfo) {
        URI redirectUri = URI.create("/swagger-ui.html");
        return Response.seeOther(redirectUri).build();
    }
}
