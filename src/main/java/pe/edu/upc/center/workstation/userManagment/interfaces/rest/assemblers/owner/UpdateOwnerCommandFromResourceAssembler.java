package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.UpdateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.UpdateOwnerRequest;

public final class UpdateOwnerCommandFromResourceAssembler {
    private UpdateOwnerCommandFromResourceAssembler() {}

    public static UpdateOwnerCommand toCommand(Long ownerId, UpdateOwnerRequest resource) {
        return new UpdateOwnerCommand(ownerId, resource.company(), resource.ruc());
    }
}
