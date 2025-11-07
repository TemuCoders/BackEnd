package pe.edu.upc.center.workstation.propertiesManagment.interfaces.acl;

import org.springframework.stereotype.Service;
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
import java.util.Optional;

@Service
public class SpaceContextFacade {

    private final SpaceCommandService spaceCommandService;
    private final SpaceQueryService spaceQueryService;

    public SpaceContextFacade(SpaceCommandService spaceCommandService, SpaceQueryService spaceQueryService) {
        this.spaceCommandService = spaceCommandService;
        this.spaceQueryService = spaceQueryService;
    }

    public Optional<SpaceResource> fetchSpaceById(Long spaceId) {
        var opt = spaceQueryService.handle(new GetSpaceByIdQuery(spaceId));
        return opt.map(SpaceResourceFromEntityAssembler::toResource);
    }

    public List<SpaceResource> fetchAllSpaces() {
        return spaceQueryService.handle(new GetAllSpacesQuery())
                .stream()
                .map(SpaceResourceFromEntityAssembler::toResource)
                .toList();
    }

    public Long createSpace(CreateSpaceResource resource) {
        var cmd = CreateSpaceCommandFromResourceAssembler.toCommand(resource);
        return spaceCommandService.handle(cmd);
    }

    public Optional<SpaceResource> updateSpace(Long spaceId,UpdateSpaceResource resource) {
        var cmd = UpdateSpaceCommandFromResourceAssembler.toCommand(spaceId,resource);
        var updated =  spaceCommandService.handle(cmd);
        return updated.map(SpaceResourceFromEntityAssembler::toResource);
    }

    public void deleteSpace(Long spaceId) {
        spaceCommandService.handle(new DeleteSpaceCommand(spaceId));
    }
}
