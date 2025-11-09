package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

import pe.edu.upc.center.workstation.shared.utils.Util;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record UpdateUserProfileRequest(

        @JsonProperty("name")
        @NotBlank
        String name,

        @JsonProperty("age")
        @Min(Util.MIN_AGE) @Max(Util.MAX_AGE)
        int age,

        @JsonProperty("location")
        @NotBlank
        String location,

        @JsonProperty("photo")
        @NotBlank
        String photo
) {}
