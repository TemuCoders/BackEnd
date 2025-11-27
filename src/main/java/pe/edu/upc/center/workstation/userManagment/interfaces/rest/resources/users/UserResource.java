package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Date;

public record UserResource(
        @JsonProperty("id") Long id,
        @JsonProperty("name") String name,
        @JsonProperty("email") String email,
        @JsonProperty("photo") String photo,
        @JsonProperty("age") int age,
        @JsonProperty("location") String location,
        @JsonProperty("registerDate")
        @JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ssXXX")
        Date registerDate
) {}