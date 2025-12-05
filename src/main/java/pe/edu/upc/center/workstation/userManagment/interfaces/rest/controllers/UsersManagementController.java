package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.*;
import io.swagger.v3.oas.annotations.responses.*;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.shared.interfaces.rest.resources.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.UserManagementContextFacade;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersManagementController {

    private final UserManagementContextFacade userManagementFacade;
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UsersManagementController(UserManagementContextFacade userManagementFacade,
                                     UserCommandService userCommandService,
                                     UserQueryService userQueryService) {
        this.userManagementFacade = userManagementFacade;
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @Operation(
            summary = "Register a new user",
            description = "Creates a new user and automatically creates Owner/Freelancer based on the role",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User registration data",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = RegisterUserRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User registered successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data or role mismatch",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "500", description = "Internal server error - Unexpected error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = InternalServerErrorResponse.class))),
            @ApiResponse(responseCode = "503", description = "Service unavailable - Persistence error",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = ServiceUnavailableResponse.class)))
    })
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody RegisterUserRequest request) {
        Long userId = userManagementFacade.registerUser(
                request.name(),
                request.email(),
                request.password(),
                request.photo(),
                request.age(),
                request.location(),
                request.roleName()
        );

        if (userId == 0L) return ResponseEntity.badRequest().build();

        var optUser = userQueryService.handle(new GetUserByIdQuery(userId));
        if (optUser.isEmpty()) return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();

        var res = UserResourceFromEntityAssembler.toResourceFromEntity(optUser.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @Operation(summary = "Retrieve a user by ID", description = "Retrieves a user using its unique ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var opt = userQueryService.handle(new GetUserByIdQuery(userId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(opt.get()));
    }

    @Operation(summary = "Retrieve all users", description = "Retrieves all registered users")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Users retrieved successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            array = @ArraySchema(schema = @Schema(implementation = UserResource.class))))
    })
    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var list = userQueryService.handle(new GetAllUsersQuery());
        var data = list.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(data);
    }

    @Operation(
            summary = "Update a user profile",
            description = "Updates an existing user with the provided profile data",
            requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
                    description = "User profile data for update",
                    required = true,
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UpdateUserProfileRequest.class))
            )
    )
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "User updated successfully",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = UserResource.class))),
            @ApiResponse(responseCode = "400", description = "Bad request - Invalid input data",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = BadRequestResponse.class))),
            @ApiResponse(responseCode = "404", description = "User not found",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON_VALUE,
                            schema = @Schema(implementation = NotFoundResponse.class)))
    })
    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(@PathVariable Long userId,
                                                   @Valid @RequestBody UpdateUserProfileRequest request) {
        var cmd = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(userId, request);
        var updated = userCommandService.handle(cmd);
        if (updated.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
    }
}
