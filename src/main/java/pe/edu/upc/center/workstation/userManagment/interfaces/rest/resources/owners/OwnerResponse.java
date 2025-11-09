package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;

public record OwnerResponse(
        @JsonProperty("id") Long id,
        @JsonProperty("company") String company,
        @JsonProperty("ruc") String ruc,
        @JsonProperty("registeredSpaceIds") List<Long> registeredSpaceIds
) {}
