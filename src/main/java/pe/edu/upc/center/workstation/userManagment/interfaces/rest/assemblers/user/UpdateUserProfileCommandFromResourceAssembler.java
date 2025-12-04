package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;

public class UpdateUserProfileCommandFromResourceAssembler {
    private UpdateUserProfileCommandFromResourceAssembler() {}

    public static UpdateUserProfileCommand toCommandFromResource(Long userId, UpdateUserProfileRequest r) {
        return new UpdateUserProfileCommand(
                userId,
                r.name(),
                r.age(),
                r.location(),
                r.photo()
        );
    }
}
