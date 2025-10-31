package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import java.util.List;

public record FreelancerResource(Long id, String userType, List<String> preferences, List<Long> bookedSpaceIds
) {}
