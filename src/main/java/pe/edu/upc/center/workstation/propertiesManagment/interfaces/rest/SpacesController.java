package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByIdQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceQueryService;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.CreateSpaceResource;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceResource;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.UpdateSpaceResource;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform.CreateSpaceCommandFromResourceAssembler;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform.SpaceResourceFromEntityAssembler;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform.UpdateSpaceCommandFromResourceAssembler;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.DELETE, RequestMethod.POST, RequestMethod.GET ,RequestMethod.PUT, RequestMethod.PATCH})
@RestController
@RequestMapping(value = "/api/v1/spaces", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Spaces", description = "Space Management Endpoints")
public class SpacesController {

    private final SpaceQueryService  spaceQueryService;
    private final SpaceCommandService  spaceCommandService;

    public SpacesController(SpaceQueryService spaceQueryService, SpaceCommandService spaceCommandService) {
        this.spaceQueryService = spaceQueryService;
        this.spaceCommandService = spaceCommandService;
    }

    @PostMapping
    public ResponseEntity<SpaceResource> createSpace(@RequestBody CreateSpaceResource resource) {
        var cmd = CreateSpaceCommandFromResourceAssembler.toCommand(resource);
        var id = spaceCommandService.handle(cmd);
        if (id == null || id == 0L) return ResponseEntity.badRequest().build();

        var opt = spaceQueryService.handle(new GetSpaceByIdQuery(id));
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();

        var res = SpaceResourceFromEntityAssembler.toResource(opt.get());
        return new ResponseEntity<>(res, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<SpaceResource>> getAllSpaces() {
        var list = spaceQueryService.handle(new GetAllSpacesQuery());
        var res = list.stream().map(SpaceResourceFromEntityAssembler::toResource).collect(Collectors.toList());
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{spaceId}")
    public ResponseEntity<SpaceResource> getSpaceById(@PathVariable Long spaceId) {
        var opt = spaceQueryService.handle(new GetSpaceByIdQuery(spaceId));
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(SpaceResourceFromEntityAssembler.toResource(opt.get()));
    }

    @PutMapping("/{spaceId}")
    public ResponseEntity<SpaceResource>  updateSpaceById(@PathVariable Long spaceId, @RequestBody UpdateSpaceResource resource) {
        var cmd = UpdateSpaceCommandFromResourceAssembler.toCommand(spaceId,resource);
        var opt = spaceCommandService.handle(cmd);
        if (opt.isEmpty()) return ResponseEntity.badRequest().build();
        return ResponseEntity.ok(SpaceResourceFromEntityAssembler.toResource(opt.get()));
    }

    @DeleteMapping
    public ResponseEntity<SpaceResource> deleteSpace(@PathVariable Long spaceId) {
        spaceCommandService.handle( new DeleteSpaceCommand(spaceId));
        return ResponseEntity.noContent().build();
    }

}
