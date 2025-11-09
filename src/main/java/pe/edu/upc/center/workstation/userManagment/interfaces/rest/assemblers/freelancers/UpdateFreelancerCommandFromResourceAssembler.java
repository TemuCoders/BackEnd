package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers;


import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.UpdateFreelancerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.UpdateFreelancerRequest;

public final class UpdateFreelancerCommandFromResourceAssembler {
    private UpdateFreelancerCommandFromResourceAssembler() {}

    public static UpdateFreelancerCommand toCommand(Long freelancerId, UpdateFreelancerRequest resource) {
        return new UpdateFreelancerCommand(freelancerId, resource.userType());
    }
}
