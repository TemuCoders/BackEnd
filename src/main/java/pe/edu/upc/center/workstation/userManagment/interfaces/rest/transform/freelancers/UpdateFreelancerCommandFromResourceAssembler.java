package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.UpdateFreelancerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.UpdateFreelancerResource;

public final class UpdateFreelancerCommandFromResourceAssembler {
    private UpdateFreelancerCommandFromResourceAssembler() {}
    public static UpdateFreelancerCommand toCommand(Long freelancerId, UpdateFreelancerResource r) {
        return new UpdateFreelancerCommand(freelancerId, r.userType());
    }
}
