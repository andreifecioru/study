package dev.afecioru.springlabs.app.api.v2.resources;

import dev.afecioru.springlabs.app.api.dto.todo.ToDoDTO;
import dev.afecioru.springlabs.core.todo.ToDo;
import dev.afecioru.springlabs.core.todo.ToDoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


@Path("/todos")
@Produces(MediaType.APPLICATION_JSON)
@Slf4j
@Component
@RequiredArgsConstructor
@Tag(name = "ToDo Resource")
public class ToDoResource {
    private final ToDoService toDoService;
    private final ModelMapper modelMapper;

    @GET
    @Operation(summary = "Get all TODOs", description = "Retrieve all TODO items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ToDoDTO.class)))
    })
    public Response getAll() {
        val toDos = toDoService.getAll().stream().map(toDo ->
            modelMapper.map(toDo, ToDoDTO.class)
        ).toList();

        return Response.ok(toDos).build();
    }

    @GET
    @Path("/{id}")
    @Operation(summary = "Get todo by ID", description = "Retrieve a specific todo item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ToDoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public Response getById(@Parameter(description = "ID of the todo to retrieve") @PathParam("id") Long id) {
        val toDo = modelMapper.map(toDoService.get(id), ToDoDTO.class);
        return Response.ok(toDo).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Create a new TODO", description = "Create a new TODO item")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo created successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ToDoDTO.class))),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Response create(@Parameter(description = "Todo to create") ToDoDTO toDoDTO) {
        val newToDo = toDoService.create(modelMapper.map(toDoDTO, ToDo.class));
        val response = modelMapper.map(newToDo, ToDoDTO.class);

        return Response.ok(response).build();
    }

    @PUT
    @Path("/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Operation(summary = "Update a TODO", description = "Update an existing TODO item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Todo updated successfully",
                content = @Content(mediaType = "application/json",
                        schema = @Schema(implementation = ToDoDTO.class))),
        @ApiResponse(responseCode = "404", description = "Todo not found"),
        @ApiResponse(responseCode = "400", description = "Invalid input")
    })
    public Response update(@Parameter(description = "ID of the todo to update") @PathParam("id") Long id,
                          @Parameter(description = "Updated todo data") ToDoDTO toDoDTO) {
        val toDo = modelMapper.map(toDoDTO, ToDo.class);
        toDo.setId(id);

        val updatedToDo = toDoService.update(toDo);
        val response = modelMapper.map(updatedToDo, ToDoDTO.class);

        return Response.ok(response).build();
    }

    @DELETE
    @Path("/{id}")
    @Operation(summary = "Delete a TODO", description = "Delete a specific TODO item by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Todo deleted successfully"),
        @ApiResponse(responseCode = "404", description = "Todo not found")
    })
    public Response delete(@Parameter(description = "ID of the todo to delete") @PathParam("id") Long id) {
        toDoService.delete(id);
        return Response.noContent().build();
    }

    @DELETE
    @Operation(summary = "Delete all TODOs", description = "Delete all TODO items")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "All todos deleted successfully")
    })
    public Response deleteAll() {
        toDoService.deleteAll();
        return Response.noContent().build();
    }
}
