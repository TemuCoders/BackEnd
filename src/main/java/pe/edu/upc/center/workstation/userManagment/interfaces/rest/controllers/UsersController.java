package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user.*;

import java.util.List;

@CrossOrigin(origins = "*", methods = { RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE })
@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Users", description = "User Management Endpoints")
public class UsersController {

    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UsersController(UserCommandService userCommandService, UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody RegisterUserRequest request) {
        var cmd = RegisterUserCommandFromResourceAssembler.toCommandFromResource(request);
        var created = userCommandService.handle(cmd);
        if (created.isEmpty()) return ResponseEntity.badRequest().build();
        var res = UserResourceFromEntityAssembler.toResourceFromEntity(created.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<UserResource> getUserById(@PathVariable Long userId) {
        var opt = userQueryService.handle(new GetUserByIdQuery(userId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(opt.get()));
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> getAllUsers() {
        var list = userQueryService.handle(new GetAllUsersQuery());
        var data = list.stream().map(UserResourceFromEntityAssembler::toResourceFromEntity).toList();
        return ResponseEntity.ok(data);
    }

    @PutMapping(path = "/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<UserResource> updateUser(@PathVariable Long userId,
                                                   @Valid @RequestBody UpdateUserProfileRequest request) {
        var cmd = UpdateUserProfileCommandFromResourceAssembler.toCommandFromResource(userId, request);
        var updated = userCommandService.handle(cmd);
        if (updated.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(UserResourceFromEntityAssembler.toResourceFromEntity(updated.get()));
    }
}

