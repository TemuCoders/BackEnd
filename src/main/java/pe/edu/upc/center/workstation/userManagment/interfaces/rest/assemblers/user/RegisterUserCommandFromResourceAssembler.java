package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.RegisterUserCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.RegisterUserRequest;

public final class RegisterUserCommandFromResourceAssembler {
    private RegisterUserCommandFromResourceAssembler() {}

    public static RegisterUserCommand toCommand(RegisterUserRequest r) {
        if (!r.email().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"))
            throw new IllegalArgumentException("[RegisterUserCommand] Invalid email address  ");
        return new RegisterUserCommand(
                r.name(),
                new EmailAddress(r.email()).address(),
                r.password(),
                r.photo(),
                r.age(),
                r.location()
        );
    }
}
