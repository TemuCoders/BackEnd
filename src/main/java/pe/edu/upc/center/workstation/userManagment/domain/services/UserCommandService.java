package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;

import java.util.Optional;

public interface UserCommandService {

    Long handle(CreateUserCommand createUserCommand);

    Optional<User> handle(RegisterUserCommand command);

    Optional<User> handle(UpdateUserProfileCommand command);

    void handle(SetUserRoleCommand command);

}
