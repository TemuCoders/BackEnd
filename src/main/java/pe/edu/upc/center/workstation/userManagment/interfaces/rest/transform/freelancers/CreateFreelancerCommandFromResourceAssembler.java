package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.CreateFreelancerResource;

public final class CreateFreelancerCommandFromResourceAssembler {
    private CreateFreelancerCommandFromResourceAssembler() {}
    public static CreateFreelancerCommand toCommand(CreateFreelancerResource r) {
        return new CreateFreelancerCommand(r.userType());
    }
}
