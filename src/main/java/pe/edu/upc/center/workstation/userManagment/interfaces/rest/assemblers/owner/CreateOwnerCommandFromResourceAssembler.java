package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.CreateOwnerRequest;

public final class CreateOwnerCommandFromResourceAssembler {
    private CreateOwnerCommandFromResourceAssembler() {}

    public static CreateOwnerCommand toCommand(CreateOwnerRequest resource) {
        return new CreateOwnerCommand(resource.company(), resource.ruc());
    }
}
