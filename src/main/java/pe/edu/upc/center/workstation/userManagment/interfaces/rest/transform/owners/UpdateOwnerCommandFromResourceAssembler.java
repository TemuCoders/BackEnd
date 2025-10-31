package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.UpdateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.UpdateOwnerResource;

public final class UpdateOwnerCommandFromResourceAssembler {
    private UpdateOwnerCommandFromResourceAssembler() {}
    public static UpdateOwnerCommand toCommand(Long ownerId, UpdateOwnerResource r) {
        return new UpdateOwnerCommand(ownerId, r.company(), r.ruc());
    }
}
