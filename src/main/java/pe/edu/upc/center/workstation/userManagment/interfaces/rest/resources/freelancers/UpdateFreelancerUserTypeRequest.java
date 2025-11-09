package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record UpdateFreelancerUserTypeRequest(
        @JsonProperty("userType") @NotBlank String userType
) {}
