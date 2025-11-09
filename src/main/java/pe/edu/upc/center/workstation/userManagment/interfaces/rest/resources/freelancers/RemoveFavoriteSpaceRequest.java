package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.freelancers;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record RemoveFavoriteSpaceRequest(
        @JsonProperty("spaceId") @NotNull @Min(1) Long spaceId
) {}