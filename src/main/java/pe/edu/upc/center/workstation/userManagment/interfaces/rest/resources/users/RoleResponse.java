package pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users;

import com.fasterxml.jackson.annotation.JsonProperty;

public record RoleResponse(
        @JsonProperty("roleName") String roleName,
        @JsonProperty("id") Long id
) {
}
