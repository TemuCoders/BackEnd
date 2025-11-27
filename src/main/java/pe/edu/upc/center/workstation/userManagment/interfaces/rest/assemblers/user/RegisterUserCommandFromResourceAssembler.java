package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;

public class RegisterUserCommandFromResourceAssembler {
    public static RegisterUserCommand toCommandFromResource(RegisterUserRequest r) {
        return new RegisterUserCommand(r.name(), r.email(), r.password(), r.photo(), r.age(), r.location());
    }
}
