package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.AddFavoriteSpaceCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.AddFavoriteSpaceResource;

public final class AddFavoriteSpaceCommandFromResourceAssembler {
    private AddFavoriteSpaceCommandFromResourceAssembler() {}
    public static AddFavoriteSpaceCommand toCommand(Long freelancerId, AddFavoriteSpaceResource r) {
        return new AddFavoriteSpaceCommand(freelancerId, r.spaceId());
    }
}
