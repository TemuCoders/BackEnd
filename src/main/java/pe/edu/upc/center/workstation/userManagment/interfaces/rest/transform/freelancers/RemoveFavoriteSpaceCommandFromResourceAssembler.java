package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.RemoveFavoriteSpaceCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.RemoveFavoriteSpaceResource;

public final class RemoveFavoriteSpaceCommandFromResourceAssembler {
    private RemoveFavoriteSpaceCommandFromResourceAssembler() {}
    public static RemoveFavoriteSpaceCommand toCommand(Long freelancerId, RemoveFavoriteSpaceResource r) {
        return new RemoveFavoriteSpaceCommand(freelancerId, r.spaceId());
    }
}
