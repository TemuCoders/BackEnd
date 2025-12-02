package pe.edu.upc.center.workstation.userManagment.interfaces.rest.controllers;

import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.DeleteOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetAllOwnersQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByIdQuery;
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

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerResponse> createOwner(@Valid @RequestBody CreateOwnerRequest resource) {
        var cmd = CreateOwnerCommandFromResourceAssembler.toCommand(resource);
        Long id = ownerCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();

        var res = OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OwnerResponse>> getAllOwners() {
        var owners = ownerQueryService.handle(new GetAllOwnersQuery());
        var res = owners.stream()
                .map(OwnerResourceFromEntityAssembler::toResponseFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerResponse> getOwnerById(@PathVariable Long ownerId) {
        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (opt.isEmpty()) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @PutMapping(value = "/{ownerId}", consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<OwnerResponse> updateOwner(@PathVariable Long ownerId,
                                                     @Valid @RequestBody UpdateOwnerRequest resource) {
        var cmd = UpdateOwnerCommandFromResourceAssembler.toCommand(ownerId, resource);
        var opt = ownerCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResponseFromEntity(opt.get()));
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long ownerId) {
        ownerCommandService.handle(new DeleteOwnerCommand(ownerId));
        return ResponseEntity.noContent().build();
    }
}
