package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleAssignment;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;

public class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {}

    public static UserResource toResourceFromEntity(User e) {
        if (e == null) throw new IllegalArgumentException("[UserResource] entity must not be null");

        RoleResponse roleResource = null;
        UserRoleAssignment role = e.getRole();

        if (role != null && role.roleName() != null) {
            roleResource = new RoleResponse(role.roleName().toString());
        }

        return new UserResource(
                e.getId(),
                e.getName(),
                e.getEmail().address(),
                e.getPhoto(),
                e.getAge(),
                e.getLocation(),
                roleResource,
                e.getRegisterDate()
        );
    }
}
