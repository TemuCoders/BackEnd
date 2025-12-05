package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.DeleteOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetAllOwnersQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByUserIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*",
        methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/owners", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Owners", description = "Owner Management Endpoints")
public class OwnersController {

    private final OwnerQueryService ownerQueryService;
    private final OwnerCommandService ownerCommandService;

    public OwnersController(OwnerQueryService ownerQueryService, OwnerCommandService ownerCommandService) {
        this.ownerQueryService = ownerQueryService;
        this.ownerCommandService = ownerCommandService;
    }

    @Operation(
            summary = "Create a new owner",
            description = "Creates a new owner with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Owner data for creation",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = CreateOwnerRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Owner created successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found after creation",
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
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest resource) {
        CreateOwnerCommand cmd = CreateOwnerCommandFromResourceAssembler.toCommand(resource);
        Long id = ownerCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        var res = OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve all owners", description = "Retrieves all registered owners")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owners retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = OwnerResponse.class))))
    })
    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        var owners = ownerQueryService.handle(new GetAllOwnersQuery());
        var res = owners.stream()
                .map(OwnerResourceFromEntityAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @Operation(summary = "Retrieve an owner by ID", description = "Retrieves an owner using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long ownerId) {
        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(
            summary = "Update an owner",
            description = "Updates an existing owner with the provided data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "Owner data for update",
                    required = true,
                    content = @Content(
                            mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateOwnerRequest.class)
                    )
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerResponse.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping(path = "/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long ownerId,
                                                     @Valid @RequestBody UpdateOwnerRequest resource) {
        var cmd = UpdateOwnerCommandFromResourceAssembler.toCommand(ownerId, resource);
        var opt = ownerCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(summary = "Delete an owner by ID", description = "Deletes an owner using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Owner deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Owner not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long ownerId) {
        ownerCommandService.handle(new DeleteOwnerCommand(ownerId));
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Retrieve an owner by user ID", description = "Retrieves the owner linked to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = OwnerResponse.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found for given user",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/by-user/{userId}")
    public ResponseEntity<OwnerResponse> getOwnerByUserId(@PathVariable Long userId) {
        var query = new GetOwnerByUserIdQuery(userId);
        var opt = ownerQueryService.handle(query);

        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @Operation(summary = "Retrieve owner ID by user ID", description = "Returns only the owner ID linked to a specific user")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Owner ID retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = Long.class))),
            @ApiResponse(responseCode = "404", description = "Owner not found for given user",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/id-by-user/{userId}")
    public ResponseEntity<Long> getOwnerIdByUserId(@PathVariable Long userId) {
        var query = new GetOwnerByUserIdQuery(userId);
        var opt = ownerQueryService.handle(query);

        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        return ResponseEntity.ok(opt.get().getId());
    }
}
