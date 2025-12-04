package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CreateFreelancerRequest(
        @JsonProperty("userId") @NotNull Long userId,
        @JsonProperty("userType") @NotBlank String userType
) {}
