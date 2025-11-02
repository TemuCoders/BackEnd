package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Freelancer;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.freelancer.*;

import java.util.*;

public interface FreelancerQueryService {
    List<Freelancer> handle(GetAllFreelancersQuery query);
    Optional<Freelancer> handle(GetFreelancerByIdQuery query);
    List<Long> handle(GetFreelancerBookingsQuery query);
    List<Long> handle(GetFreelancerFavoriteSpacesQuery query);
    List<String> handle(GetFreelancerPreferencesQuery query);
}
