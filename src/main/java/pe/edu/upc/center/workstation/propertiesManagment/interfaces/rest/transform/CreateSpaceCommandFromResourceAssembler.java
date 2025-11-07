package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.CreateSpaceResource;

public final class CreateSpaceCommandFromResourceAssembler {
    private CreateSpaceCommandFromResourceAssembler() {}

    public static CreateSpaceCommand toCommand(CreateSpaceResource resource) {
        return new CreateSpaceCommand(
                resource.name(),
                resource.spaceType(),
                resource.ownerId(),
                resource.price(),
                resource.capacity(),
                resource.description(),
                resource.available(),
                resource.location()
        );
    }
}
