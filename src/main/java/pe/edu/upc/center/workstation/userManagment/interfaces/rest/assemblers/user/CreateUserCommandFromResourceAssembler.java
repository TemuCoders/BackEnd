package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user;


import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.CreateUserCommand;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.RegisterUserRequest;

public final class CreateUserCommandFromResourceAssembler {

    private CreateUserCommandFromResourceAssembler() { }

    public static CreateUserCommand toCommand(RegisterUserRequest resource) {
        if (!resource.email().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"))
            throw new IllegalArgumentException("[CreateUserCOmmand] Invalid email address  ");
        return new CreateUserCommand(
                resource.name(),
                new EmailAddress(resource.email()).address(),
                resource.password(),
                resource.photo(),
                resource.age(),
                resource.location()
        );
    }
}
