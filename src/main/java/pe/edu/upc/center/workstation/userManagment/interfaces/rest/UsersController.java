package pe.edu.upc.center.workstation.userManagment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.users.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {
    private final UserQueryService userQueryService;
    private final UserCommandService userCommandService;

    public UsersController(UserQueryService userQueryService, UserCommandService userCommandService) {
        this.userQueryService = userQueryService;
        this.userCommandService = userCommandService;
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@RequestBody CreateUserResource resource) {
        var createUserCommand = CreateUserCommandFromResourceAssembler.toCommand(resource);
        var userId = this.userCommandService.handle(createUserCommand);

        if (userId == null || userId.equals(0L)) return ResponseEntity.badRequest().build();

        var optionalUser = this.userQueryService.handle(new GetUserByIdQuery(userId.intValue()));
        var userResource = UserResourceFromEntityAssembler.toResource(optionalUser.get());
        return new ResponseEntity<>(userResource, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var users = this.userQueryService.handle(new GetAllUsersQuery());
        var resources = users.stream()
                .map(UserResourceFromEntityAssembler::toResource)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable int userId) {
        var optionalUser = this.userQueryService.handle(new GetUserByIdQuery(userId));
        if (optionalUser.isEmpty()) return ResponseEntity.badRequest().build();
        var userResource = UserResourceFromEntityAssembler.toResource(optionalUser.get());
        return ResponseEntity.ok(userResource);
    }

    @PutMapping("/{userId}")
    public ResponseEntity<UserResource> updateUser(@PathVariable int userId, @RequestBody UpdateUserResource resource) {
        var updateUserCommand = UpdateUserCommandFromResourceAssembler.toCommand(userId, resource);
        var optionalUser = this.userCommandService.handle(updateUserCommand);

        if (optionalUser.isEmpty()) return ResponseEntity.badRequest().build();
        var userResource = UserResourceFromEntityAssembler.toResource(optionalUser.get());
        return ResponseEntity.ok(userResource);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<?> deleteUser(@PathVariable int userId) {
        this.userCommandService.handle(new DeleteUserAccountCommand(userId));
        return ResponseEntity.noContent().build();
    }
}
