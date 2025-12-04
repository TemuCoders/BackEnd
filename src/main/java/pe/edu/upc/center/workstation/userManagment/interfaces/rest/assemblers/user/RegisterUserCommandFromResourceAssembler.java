package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;

public class RegisterUserCommandFromResourceAssembler {
    private RegisterUserCommandFromResourceAssembler() {}

    public static RegisterUserCommand toCommandFromResource(RegisterUserRequest r) {

        String email = r.email() == null ? null : r.email().trim().toLowerCase();
        return new RegisterUserCommand(
                r.name(),
                email,
                r.password(),
                r.photo(),
                r.age(),
                r.location()
        );
    }
}
