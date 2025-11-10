package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.UpdateUserProfileCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.UpdateUserProfileRequest;

public final class UpdateUserProfileCommandFromResourceAssembler {
    private UpdateUserProfileCommandFromResourceAssembler() {}

    public static UpdateUserProfileCommand toCommand(Long userId, UpdateUserProfileRequest resource) {
        return new UpdateUserProfileCommand(
                userId,
                resource.name(),
                resource.age(),
                resource.location(),
                resource.photo()
        );
    }
}