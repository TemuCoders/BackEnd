package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;


import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/freelancers", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Freelancers", description = "Freelancer Management Endpoints")
public class FreelancersController {

    private final FreelancerQueryService freelancerQueryService;
    private final FreelancerCommandService freelancerCommandService;

    public FreelancersController(FreelancerQueryService freelancerQueryService,
                                 FreelancerCommandService freelancerCommandService) {
        this.freelancerQueryService = freelancerQueryService;
        this.freelancerCommandService = freelancerCommandService;
    }

    @Operation(
            summary = "Create a new freelancer",
            description = "Creates a new freelancer with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Freelancer data for creation",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateFreelancerRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Freelancer created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FreelancerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found after creation",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> createFreelancer(@Valid @RequestBody CreateFreelancerRequest resource) {
        var cmd = CreateFreelancerCommandFromResourceAssembler.toCommand(resource);
        Long id = freelancerCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        var res = FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all freelancers", description = "Retrieves all registered freelancers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancers retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = FreelancerResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<FreelancerResponse>> getAllFreelancers() {
        var list = freelancerQueryService.handle(new GetAllFreelancersQuery());
        var res = list.stream()
                .map(FreelancerResourceFromEntityAssembler::toResponseFromEntity)
                .toList();
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Retrieve a freelancer by ID", description = "Retrieves a freelancer using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FreelancerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{freelancerId}")
    public ResponseEntity<FreelancerResponse> getFreelancerById(@PathVariable long freelancerId) {
        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(
            summary = "Update a freelancer",
            description = "Updates an existing freelancer with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Freelancer data for update",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateFreelancerRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FreelancerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping(path = "/{freelancerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> updateFreelancer(@PathVariable long freelancerId,
                                                               @Valid @RequestBody UpdateFreelancerRequest resource) {
        var cmd = UpdateFreelancerCommandFromResourceAssembler.toCommand(freelancerId, resource);
        var opt = freelancerCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(
            summary = "Update freelancer user type",
            description = "Updates only the userType field for a freelancer",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User type data for update",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateFreelancerUserTypeRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User type updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FreelancerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PatchMapping(path = "/{freelancerId}/user-type", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<FreelancerResponse> updateUserType(@PathVariable long freelancerId,
                                                             @Valid @RequestBody UpdateFreelancerUserTypeRequest resource) {
        freelancerCommandService.handle(new UpdateFreelancerUserTypeCommand(freelancerId, resource.userType()));
        var opt = freelancerQueryService.handle(new GetFreelancerByIdQuery(freelancerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(summary = "Retrieve freelancer preferences", description = "Retrieves the preferences list for a freelancer")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Preferences retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = String.class)))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{freelancerId}/preferences")
    public ResponseEntity<List<String>> getPreferences(@PathVariable long freelancerId) {
        var prefs = freelancerQueryService.handle(new GetFreelancerPreferencesQuery(freelancerId));
        return ResponseEntity.ok(prefs);
    }

    @Operation(summary = "Delete a freelancer by ID", description = "Deletes a freelancer using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Freelancer deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Freelancer not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @DeleteMapping("/{freelancerId}")
    public ResponseEntity<Void> deleteFreelancer(@PathVariable long freelancerId) {
        freelancerCommandService.handle(new DeleteFreelancerCommand(freelancerId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve a freelancer by user ID", description = "Retrieves the freelancer linked to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = FreelancerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found for given user",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<FreelancerResponse> getFreelancerByUserId(@PathVariable Long userId) {
        var query = new GetFreelancerByUserIdQuery(userId);
        var opt = freelancerQueryService.handle(query);

        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(FreelancerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(summary = "Retrieve freelancer ID by user ID", description = "Returns only the freelancer ID linked to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Freelancer ID retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Freelancer not found for given user",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/id-by-user/{userId}")
    public ResponseEntity<Long> getFreelancerIdByUserId(@PathVariable Long userId) {
        var query = new GetFreelancerByUserIdQuery(userId);
        var opt = freelancerQueryService.handle(query);

        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(opt.get().getId());
    }
}
