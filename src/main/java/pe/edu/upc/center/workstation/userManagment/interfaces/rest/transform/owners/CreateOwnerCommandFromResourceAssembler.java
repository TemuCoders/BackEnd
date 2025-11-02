package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.*;

public final class CreateOwnerCommandFromResourceAssembler {
    private CreateOwnerCommandFromResourceAssembler() {}
    public static CreateOwnerCommand toCommand(CreateOwnerResource r) {
        return new CreateOwnerCommand(r.company(), r.ruc());
    }
}
