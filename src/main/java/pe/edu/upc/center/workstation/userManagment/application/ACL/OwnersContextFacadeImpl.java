package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.RegisterSpaceForOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.RemoveSpaceForOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.ExistsOwnerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByRucQuery;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.OwnerContextFacade;

@Service
public class OwnersContextFacadeImpl implements OwnerContextFacade {

    private final OwnerCommandService ownerCommandService;
    private final OwnerQueryService ownerQueryService;

    public OwnersContextFacadeImpl(
            OwnerCommandService ownerCommandService,
            OwnerQueryService ownerQueryService
    ) {
        this.ownerCommandService = ownerCommandService;
        this.ownerQueryService = ownerQueryService;
    }

    @Override
    public Long createOwner(String company, String ruc) {
        var cmd = new CreateOwnerCommand(company, ruc);
        Long ownerId = ownerCommandService.handle(cmd);
        return (ownerId == null) ? 0L : ownerId;
    }

    @Override
    public Long fetchOwnerIdByRuc(String ruc) {
        var q = new GetOwnerByRucQuery(ruc);
        var owner = ownerQueryService.handle(q);
        return owner.isEmpty() ? 0L : owner.get().getId();
    }

    @Override
    public void registerSpaceForOwner(Long ownerId, Long spaceId) {
        ownerCommandService.handle(new RegisterSpaceForOwnerCommand(ownerId, spaceId));
    }

    @Override
    public void removeSpaceForOwner(Long ownerId, Long spaceId) {
        ownerCommandService.handle(new RemoveSpaceForOwnerCommand(ownerId, spaceId));
    }

    @Override
    public boolean existsOwnerById(Long ownerId) {
        return ownerQueryService.handle(new ExistsOwnerByIdQuery(ownerId));
    }
}
