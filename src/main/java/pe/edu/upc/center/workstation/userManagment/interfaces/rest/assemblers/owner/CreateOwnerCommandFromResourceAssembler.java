package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.CreateOwnerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.CreateOwnerRequest;

public final class CreateOwnerCommandFromResourceAssembler {
    private CreateOwnerCommandFromResourceAssembler() {}

    public static CreateOwnerCommand toCommand(CreateOwnerRequest r) {
        return new CreateOwnerCommand(
                r.userId(),
                r.company() == null ? null : r.company(),
                r.ruc() == null ? null : r.ruc().trim()

        );
    }
}
