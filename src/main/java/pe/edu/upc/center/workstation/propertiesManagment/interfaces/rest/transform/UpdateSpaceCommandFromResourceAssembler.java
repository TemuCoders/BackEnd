package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.UpdateSpaceResource;

public final class UpdateSpaceCommandFromResourceAssembler {
    private UpdateSpaceCommandFromResourceAssembler() {}

    public static UpdateSpaceCommand toCommand(Long spaceId, UpdateSpaceResource r) {
        return new UpdateSpaceCommand(
          spaceId,
                r.name(),
                r.spaceType(),
                r.capacity(),
                r.price(),
                r.description(),
                r.location()
        );
    }
}

