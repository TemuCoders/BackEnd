package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.FreelancerResource;

import java.util.List;

public final class FreelancerResourceFromEntityAssembler {
    private FreelancerResourceFromEntityAssembler() {}
    public static FreelancerResource toResource(Freelancer e) {
        return new FreelancerResource(
                e.getId(),
                e.getUserType(),
                e.getPreferences(),
                e.getBookedSpaceIds()
        );
    }
    public static List<FreelancerResource> toResourceList(List<Freelancer> entities) {
        return entities.stream().map(FreelancerResourceFromEntityAssembler::toResource).toList();
    }
}
