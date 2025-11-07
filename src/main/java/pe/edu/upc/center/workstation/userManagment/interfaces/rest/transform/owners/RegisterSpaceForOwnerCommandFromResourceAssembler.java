package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.RegisterSpaceForOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.RegisterSpaceResource;

public final class RegisterSpaceForOwnerCommandFromResourceAssembler {
    private RegisterSpaceForOwnerCommandFromResourceAssembler() { }

    public static RegisterSpaceForOwnerCommand toCommand(Long ownerId, RegisterSpaceResource resource) {
        return new RegisterSpaceForOwnerCommand(ownerId, resource.spaceId());
    }
}
