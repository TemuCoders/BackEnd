package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners.*;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerContextFacade {

    private final OwnerCommandService ownerCommandService;
    private final OwnerQueryService ownerQueryService;

    public OwnerContextFacade(OwnerCommandService ownerCommandService,
                              OwnerQueryService ownerQueryService) {
        this.ownerCommandService = ownerCommandService;
        this.ownerQueryService = ownerQueryService;
    }

    public Optional<OwnerResource> fetchOwnerById(Long ownerId) {
        var opt = ownerQueryService.handle(new GetOwnerByIdQuery(ownerId));
        return opt.map(OwnerResourceFromEntityAssembler::toResource);
    }

    public List<OwnerResource> fetchAllOwners() {
        return ownerQueryService.handle(new GetAllOwnersQuery())
                .stream()
                .map(OwnerResourceFromEntityAssembler::toResource)
                .toList();
    }

    public List<Long> getRegisteredSpaceIds(Long ownerId) {
        return ownerQueryService.handle(new GetOwnerByIdQuery(ownerId))
                .map(Owner::getRegisteredSpaceIds)
                .orElse(List.of());
    }

    public Long createOwner(CreateOwnerResource resource) {
        var cmd = CreateOwnerCommandFromResourceAssembler.toCommand(resource);
        return ownerCommandService.handle(cmd);
    }

    public Optional<OwnerResource> updateOwner(Long ownerId, UpdateOwnerResource resource) {
        var cmd = UpdateOwnerCommandFromResourceAssembler.toCommand(ownerId, resource);
        var updated = ownerCommandService.handle(cmd);
        return updated.map(OwnerResourceFromEntityAssembler::toResource);
    }

    public void deleteOwner(Long ownerId) {
        ownerCommandService.handle(new DeleteOwnerCommand(ownerId));
    }

    public void registerSpace(Long ownerId, long spaceId) {
        ownerCommandService.handle(new RegisterSpaceToOwnerCommand(ownerId, spaceId));
    }

    public void removeSpace(Long ownerId, long spaceId) {
        ownerCommandService.handle(new RemoveSpaceFromOwnerCommand(ownerId, spaceId));
    }
}
