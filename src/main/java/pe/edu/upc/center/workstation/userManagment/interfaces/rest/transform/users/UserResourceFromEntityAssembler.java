package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.users;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.UserResource;

public class UserResourceFromEntityAssembler {
    private UserResourceFromEntityAssembler() {}
    public static UserResource toResource(User e) {
        return new UserResource(
                e.getId().intValue(),
                e.getName(),
                e.getEmail(),
                e.getPhoto(),
                e.getAge(),
                e.getLocation(),
                e.getRegisterDate()
        );
    }
}
