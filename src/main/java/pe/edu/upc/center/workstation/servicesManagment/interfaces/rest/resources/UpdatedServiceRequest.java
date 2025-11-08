package pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;

public record UpdatedServiceRequest (
    @JsonProperty("serviceId")
    @NotNull
    Long serviceId,

    @JsonProperty("name")
    @NotNull
    String name,

    @JsonProperty("description")
    @NotNull
    String  description,

    @JsonProperty("price")
    @NotNull
    Double price
)
{}
