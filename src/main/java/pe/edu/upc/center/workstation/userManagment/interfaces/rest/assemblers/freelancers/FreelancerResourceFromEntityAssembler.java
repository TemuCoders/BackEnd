package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.freelancers;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers.FreelancerResponse;

import java.util.ArrayList;
import java.util.Objects;

public final class FreelancerResourceFromEntityAssembler {
    private FreelancerResourceFromEntityAssembler() {}

    public static FreelancerResponse toResponseFromEntity(Freelancer entity) {
        Objects.requireNonNull(entity, "[FreelancerResourceFromEntityAssembler] entity es null");
        var preferences = new ArrayList<>(entity.getPreferences());
        return new FreelancerResponse(
                entity.getId(),
                entity.getUserId(),
                entity.getUserType(),
                preferences
        );
    }
}