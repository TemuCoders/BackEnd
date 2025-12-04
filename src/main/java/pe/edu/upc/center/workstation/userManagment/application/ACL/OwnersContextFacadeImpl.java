package pe.edu.upc.center.workstation.userManagment.application.ACL;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.RegisterSpaceForOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.RemoveSpaceForOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.SetUserRoleCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.ExistsOwnerByIdQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.GetOwnerByRucQuery;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerCommandService;
import pe.edu.upc.center.workstation.userManagment.domain.services.OwnerQueryService;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.interfaces.acl.OwnerContextFacade;

@Service
public class OwnersContextFacadeImpl implements OwnerContextFacade {

    private final OwnerCommandService ownerCommandService;
    private final OwnerQueryService ownerQueryService;
    private final UserCommandService userCommandService;

    public OwnersContextFacadeImpl(OwnerCommandService ownerCommandService,
                                   OwnerQueryService ownerQueryService,
                                   UserCommandService userCommandService) {
        this.ownerCommandService = ownerCommandService;
        this.ownerQueryService = ownerQueryService;
        this.userCommandService = userCommandService;
    }

    @Override
    @Transactional
    public Long createOwner(Long userId, String company, String ruc) {
        Long ownerId = ownerCommandService.handle(new CreateOwnerCommand(userId, company, ruc));
        userCommandService.handle(new SetUserRoleCommand(userId, UserRoleName.OWNER));

        return ownerId;
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
