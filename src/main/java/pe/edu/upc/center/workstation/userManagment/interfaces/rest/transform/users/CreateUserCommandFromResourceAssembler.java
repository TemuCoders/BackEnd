package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.users;

import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.RegisterUserCommand;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.CreateUserResource;

public class CreateUserCommandFromResourceAssembler {
    private CreateUserCommandFromResourceAssembler() {}
    public static RegisterUserCommand toCommand(CreateUserResource r) {
        return new RegisterUserCommand(r.name(), r.email(), r.password(), r.photo(), r.age(), r.location());
    }
}
