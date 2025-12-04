package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record FreelancerResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("userId") Long userId,
        @JsonProperty("userType") String userType,
        @JsonProperty("preferences") List<String> preferences
) {}
