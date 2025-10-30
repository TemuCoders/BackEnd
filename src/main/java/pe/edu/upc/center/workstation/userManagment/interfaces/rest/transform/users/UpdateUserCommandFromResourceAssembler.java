package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.users;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.UpdateUserProfileCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.UpdateUserResource;

public class UpdateUserCommandFromResourceAssembler {
    private UpdateUserCommandFromResourceAssembler() {}
    public static UpdateUserProfileCommand toCommand(int id, UpdateUserResource r) {
        return new UpdateUserProfileCommand(id, r.name(), r.age(), r.location(), r.photo());
    }
}
