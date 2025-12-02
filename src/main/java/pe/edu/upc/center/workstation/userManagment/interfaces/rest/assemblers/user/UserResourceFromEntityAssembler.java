package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;

public class UserResourceFromEntityAssembler {
    public static UserResource toResourceFromEntity(User e) {
        return new UserResource(e.getId(), e.getName(), e.getEmail(), e.getPhoto(), e.getAge(), e.getLocation(), e.getRegisterDate());
    }
}
