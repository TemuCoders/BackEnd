package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;

import java.util.Optional;

public interface UserCommandService {
    Long handle(RegisterUserCommand c);
    Optional<User> handle(UpdateUserProfileCommand c);
    void handle(DeleteUserAccountCommand c);
    void handle(LoginUserCommand c);
    void handle(LogoutUserCommand c);
}