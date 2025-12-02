package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.shared.utils.Util;

public record UpdateSpaceRequest(

        @JsonProperty("spaceId")
        @NotNull
        Long spaceId,

        @JsonProperty("name")
        @NotBlank @NotNull
        String name,

        @JsonProperty("ownerId")
        OwnerId ownerId,

        @JsonProperty("spaceType")
        @NotNull @NotBlank
        String spaceType,

        @JsonProperty("price")
        @NotNull
        Double price,

        @JsonProperty("capacity")
        @NotNull
        Integer capacity,

        @JsonProperty("description")
        @NotNull
        String description,

        @JsonProperty("available")
        @NotNull
        Boolean available,

        @JsonProperty("street")
        @NotNull @NotBlank
        String street,

        @JsonProperty("streetNumber")
        @NotNull @NotBlank
        @Size(min = Util.STREET_NUMBER_MIN_LENGTH, max = Util.STREET_NUMBER_MAX_LENGTH)
        String streetNumber,

        @JsonProperty("city")
        @NotNull @NotBlank
        String city,

        @JsonProperty("postalCode")
        @NotNull @NotBlank
        @Pattern(regexp = "\\d{5}", message = "Postal code must be 5 digits")
        @Size(min = Util.POSTAL_CODE_LENGTH, max = Util.POSTAL_CODE_LENGTH)
        String postalCode,

        @JsonProperty("img")
        @NotNull @NotBlank
        String img
) {
}
