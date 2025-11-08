package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByIdQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceQueryService;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.assemblers.SpaceAssembler;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.CreateSpaceRequest;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceMinimalResponse;

import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceResponse;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.UpdateSpaceRequest;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.ServiceUnavailableResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing profiles.
 */
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET ,RequestMethod.PUT, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/spaces", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Spaces", description = "Space Management Endpoints")
public class SpacesController {

    private final SpaceQueryService  spaceQueryService;
    private final SpaceCommandService  spaceCommandService;

    /**
     * Constructor for SpacesController
     *
     * @param spaceQueryService the service for handling space queries
     * @param spaceCommandService the service for handling space commands
     */
    public SpacesController(SpaceQueryService spaceQueryService, SpaceCommandService spaceCommandService) {
        this.spaceQueryService = spaceQueryService;
        this.spaceCommandService = spaceCommandService;
    }

    /**
     * Endpoint to create a new space.
     *
     * @param request the space data to be created
     * @return a ResponseEntity containing the created space resource or a bad request status
     *     if creation fails
     */
    @Operation(summary = "Create a new space",
            description = "Creates a new space with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Space data for creation", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateSpaceRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Space created successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SpaceMinimalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })


    @PostMapping
    public ResponseEntity<SpaceMinimalResponse> createSpace(
            @Valid @RequestBody CreateSpaceRequest request) {
        var createSpaceCommand = SpaceAssembler.toCommandFromRequest(request);
        var spaceId = this.spaceCommandService.handle(createSpaceCommand);

        if(Objects.isNull(spaceId) || spaceId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getSpaceIdQuery = new GetSpaceByIdQuery(spaceId);
        var optionalSpace = this.spaceQueryService.handle(getSpaceIdQuery);

        var spaceMinimalResponse = SpaceAssembler.toResponseMinimalFromEntity(optionalSpace.get());
        return new ResponseEntity<>(spaceMinimalResponse, HttpStatus.CREATED);
    }

    /**
     * Endpoint to retrieve all profiles or filter by age.
     *
     * @return a list of ResponseMinimalEntity
     */
    @Operation( summary = "Retrieve all spaces",
            description = "Retrieves all spaces provided"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Profiles retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = SpaceMinimalResponse.class)) ))
    })
    @GetMapping
    public ResponseEntity<List<SpaceMinimalResponse>> getAllSpaces(
    ) {
        List<Space> spaces;
        var getAllSpacesQuery = new GetAllSpacesQuery();
        spaces = this.spaceQueryService.handle(getAllSpacesQuery);

        var spaceMinimalResponses = spaces.stream()
                .map(SpaceAssembler::toResponseMinimalFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(spaceMinimalResponses);
    }

    /**
     * Endpoint to retrieve a space by its ID.
     *
     * @param spaceId the ID of the space to be retrieved
     * @return a ResponseEntity containing the space resource or a bad request status if not found
     */
    @Operation(summary = "Retrieve a space by its ID",
            description = "Retrieves a space using its unique ID"
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Spaces retrieved successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SpaceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResponse> getSpaceById(@PathVariable Long spaceId) {
        var getSpaceByIdQuery = new GetSpaceByIdQuery(spaceId);
        var optionalSpace = this.spaceQueryService.handle(getSpaceByIdQuery);
        var spaceResponse = SpaceAssembler.toResponseFromEntity(optionalSpace.get());
        return ResponseEntity.ok(spaceResponse);
    }

    /**
     * Endpoint to update an existing space.
     *
     * @param spaceId the ID of the space to be updated
     * @param request the updated space data
     * @return a ResponseEntity containing the updated space resource or a bad request status if the update fails
     */
    @Operation(summary = "Update an existing space",
            description = "Update an existing space with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Space data for update", required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateSpaceRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Space updated successfully",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = SpaceMinimalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Not found - Space not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResponse> updateSpace(@PathVariable Long spaceId,
                                                            @Valid @RequestBody UpdateSpaceRequest request) {
        var updateSpaceCommand = SpaceAssembler.toCommandFromRequest(spaceId, request);
        var optionalSpace = this.spaceCommandService.handle(updateSpaceCommand);

        if (optionalSpace.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var spaceResource = SpaceAssembler.toResponseFromEntity(optionalSpace.get());
        return ResponseEntity.ok(spaceResource);
    }

    /**
     * Endpoint to delete a space by its ID.
     *
     * @param spaceId the ID of the space to be deleted
     * @return a ResponseEntity with no content if deletion is successful
     */
    @Operation(summary = "Delete a space by its ID",
            description = "Deletes a space using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Space deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Not found - Related resource not found",
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
    })
    @DeleteMapping("/{spaceId}")
    public ResponseEntity<?> deleteSpace(@PathVariable Long spaceId) {
        var deleteSpaceCommand = new DeleteSpaceCommand(spaceId);
        this.spaceCommandService.handle(deleteSpaceCommand);
        return ResponseEntity.noContent().build();
    }

}
