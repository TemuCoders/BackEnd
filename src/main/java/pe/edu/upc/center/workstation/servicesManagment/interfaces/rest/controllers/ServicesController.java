package pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.controllers;

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
import org.springframework.web.bind.annotation.CrossOrigin;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceBySpaceIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceCommandService;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceQueryService;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.assemblers.ServiceAssembler;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.ServiceMinimalResponse;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.UpdatedServiceRequest;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.BadRequestResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.InternalServerErrorResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.NotFoundResponse;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.ServiceUnavailableResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * REST controller for managing Services.
 */
@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT})
@RestController
@RequestMapping(value = "/api/v1/services", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Services", description = "Service Management Endpoints")
public class ServicesController {
    private final ServiceQueryService serviceQueryService;
    private final ServiceCommandService serviceCommandService;

    /**
     * Constructor for ServiceController.
     *
     * @param serviceQueryService the service for handling service queries
     * @param serviceCommandService the service for handling service commands
     */
    public ServicesController(ServiceQueryService serviceQueryService, ServiceCommandService serviceCommandService) {
        this.serviceQueryService = serviceQueryService;
        this.serviceCommandService = serviceCommandService;
    }


    @Operation(summary = "Create a new service",
            description = "Creates a new service with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Service data for creation", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateServiceRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Service created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceMinimalResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping
    public ResponseEntity<ServiceMinimalResponse> createService(@Valid @RequestBody CreateServiceRequest request) {

        var createCommand = ServiceAssembler.toCommandFromRequest(request);
        var serviceId = this.serviceCommandService.handle(createCommand);

        if (Objects.isNull(serviceId) || serviceId.equals(0L)) {
            return ResponseEntity.badRequest().build();
        }

        var getByIdQuery = new GetServiceByIdQuery(serviceId);
        var optionalService = this.serviceQueryService.handle(getByIdQuery);

        var minimalResponse = ServiceAssembler.toResponseMinimalFromEntity(optionalService.get());
        return new ResponseEntity<>(minimalResponse, HttpStatus.CREATED);
    }


    @Operation(summary = "Retrieve all services", description = "Retrieves all registered services")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Services retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ServiceMinimalResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<ServiceMinimalResponse>> getAllServices() {
        var query = new GetAllServicesQuery();
        var services = this.serviceQueryService.handle(query);

        var responses = services.stream()
                .map(ServiceAssembler::toResponseMinimalFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Retrieve a service by ID", description = "Retrieves a service using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> getServiceById(@PathVariable Long serviceId) {
        var query = new GetServiceByIdQuery(serviceId);
        var optionalService = this.serviceQueryService.handle(query);

        if (optionalService.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var response = ServiceAssembler.toResponseFromEntity(optionalService.get());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Retrieve services by space ID", description = "Retrieves all services linked to a specific space")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Services retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = ServiceMinimalResponse.class))))
    })
    @GetMapping("/space/{spaceId}")
    public ResponseEntity<List<ServiceMinimalResponse>> getServicesBySpaceId(@PathVariable Long spaceId) {
        var space = new SpaceId(spaceId);
        var query = new GetServiceBySpaceIdQuery(space);
        var services = this.serviceQueryService.handle(query);

        var responses = services.stream()
                .map(ServiceAssembler::toResponseMinimalFromEntity)
                .collect(Collectors.toList());

        return ResponseEntity.ok(responses);
    }


    @Operation(summary = "Update a service",
            description = "Updates an existing service with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Service data for update", required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdatedServiceRequest.class)))
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Service updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Service not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping("/{serviceId}")
    public ResponseEntity<ServiceResponse> updateService(@PathVariable Long serviceId,
                                                         @Valid @RequestBody UpdatedServiceRequest request) {
        var updateCommand = ServiceAssembler.toCommandFromRequest(serviceId, request);
        var optionalService = this.serviceCommandService.handle(updateCommand);

        if (optionalService.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var response = ServiceAssembler.toResponseFromEntity(optionalService.get());
        return ResponseEntity.ok(response);
    }


    @Operation(summary = "Delete a service by ID", description = "Deletes a service using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Service deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Service not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @DeleteMapping("/{serviceId}")
    public ResponseEntity<?> deleteService(@PathVariable Long serviceId) {
        var deleteCommand = new DeleteServiceCommand(serviceId);
        this.serviceCommandService.handle(deleteCommand);
        return ResponseEntity.noContent().build();
    }
}
