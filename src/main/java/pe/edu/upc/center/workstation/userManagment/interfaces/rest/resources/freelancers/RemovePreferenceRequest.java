package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;

public record RemovePreferenceRequest(
        @JsonProperty("tag") @NotBlank String tag
) {}
