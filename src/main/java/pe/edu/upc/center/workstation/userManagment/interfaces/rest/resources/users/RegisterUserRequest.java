package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

import pe.edu.upc.center.workstation.shared.utils.Util;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.*;


public record RegisterUserRequest(

        @JsonProperty("name")
        @NotNull @NotBlank
        String name,

        @JsonProperty("email")
        @NotNull @NotBlank @Email
        String email,

        @JsonProperty("password")
        @NotNull @NotBlank @Size(min = 8)
        String password,

        @JsonProperty("photo")
        @NotNull @NotBlank
        String photo,

        @JsonProperty("age")
        @Min(Util.MIN_AGE) @Max(Util.MAX_AGE)
        int age,

        @JsonProperty("location")
        @NotNull @NotBlank
        String location
) {}
