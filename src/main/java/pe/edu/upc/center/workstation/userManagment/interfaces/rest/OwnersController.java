package pe.edu.upc.center.workstation.userManagment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.GET, RequestMethod.PUT, RequestMethod.DELETE, RequestMethod.PATCH})
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

    @PostMapping
    public ResponseEntity<OwnerResource> createOwner(@RequestBody CreateOwnerResource resource) {
        var cmd = CreateOwnerCommandFromResourceAssembler.toCommand(resource);
        var id = ownerCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();

        var res = OwnerResourceFromEntityAssembler.toResource(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<OwnerResource>> getAllOwners() {
        var list = ownerQueryService.handle(new GetAllOwnersQuery());
        var res = list.stream().map(OwnerResourceFromEntityAssembler::toResource).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{ownerId}")
    public ResponseEntity<OwnerResource> getOwnerById(@PathVariable Long ownerId) {
        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResource(opt.get()));
    }

    @PutMapping("/{ownerId}")
    public ResponseEntity<OwnerResource> updateOwner(@PathVariable Long ownerId, @RequestBody UpdateOwnerResource resource) {
        var cmd = UpdateOwnerCommandFromResourceAssembler.toCommand(ownerId, resource);
        var opt = ownerCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResource(opt.get()));
    }

    @DeleteMapping("/{ownerId}")
    public ResponseEntity<Void> deleteOwner(@PathVariable Long ownerId) {
        ownerCommandService.handle(new DeleteOwnerCommand(ownerId));
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/{ownerId}/spaces")
    public ResponseEntity<OwnerResource> registerSpace(
            @PathVariable Long ownerId,
            @RequestBody RegisterSpaceResource resource) {
        var cmd = RegisterSpaceForOwnerCommandFromResourceAssembler.toCommand(ownerId, resource);
        ownerCommandService.handle(cmd);
        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(OwnerResourceFromEntityAssembler.toResource(opt.get()));
    }

    @DeleteMapping("/{ownerId}/spaces/{spaceId}")
    public ResponseEntity<Void> removeSpace(
            @PathVariable long ownerId,
            @PathVariable long spaceId) {
        ownerCommandService.handle(new RemoveSpaceForOwnerCommand(ownerId, spaceId));
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{ownerId}/spaces")
    public ResponseEntity<List<Long>> getRegisteredSpaces(@PathVariable Long ownerId) {
        var ids = ownerQueryService.handle(new GetOwnerRegisteredSpacesQuery(ownerId));
        return ResponseEntity.ok(ids);
    }
}
