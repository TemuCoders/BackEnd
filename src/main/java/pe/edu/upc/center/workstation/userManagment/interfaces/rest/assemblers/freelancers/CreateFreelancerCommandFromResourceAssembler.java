package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.CreateFreelancerCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.CreateFreelancerRequest;

public final class CreateFreelancerCommandFromResourceAssembler {
    private CreateFreelancerCommandFromResourceAssembler() {}

    public static CreateFreelancerCommand toCommand(CreateFreelancerRequest resource) {
        return new CreateFreelancerCommand(resource.userType());
    }
}
