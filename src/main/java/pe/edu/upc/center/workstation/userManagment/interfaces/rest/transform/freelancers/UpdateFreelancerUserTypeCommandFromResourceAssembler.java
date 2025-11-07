package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.UpdateFreelancerUserTypeCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.UpdateFreelancerUserTypeResource;

public final class UpdateFreelancerUserTypeCommandFromResourceAssembler {
    private UpdateFreelancerUserTypeCommandFromResourceAssembler() {}
    public static UpdateFreelancerUserTypeCommand toCommand(long freelancerId,
                                                            UpdateFreelancerUserTypeResource resource) {
        return new UpdateFreelancerUserTypeCommand(freelancerId, resource.userType());
    }
}
