package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.FreelancerResponse;

public final class FreelancerResourceFromEntityAssembler {
    private FreelancerResourceFromEntityAssembler() {}

    public static FreelancerResponse toResource(Freelancer entity) {
        return new FreelancerResponse(
                entity.getId(),
                entity.getUserType(),
                entity.getPreferences()


        );
    }
}