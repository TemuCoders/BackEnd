package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.user.*;

import java.util.*;

@Service
public class UserManagementContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserManagementContextFacade(UserCommandService userCommandService,
                                       UserQueryService userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = userQueryService;
    }

    public Optional<UserResponse> fetchUserById(Long userId) {
        var opt = userQueryService.handle(new GetUserByIdQuery(userId));
        return opt.map(UserResourceFromEntityAssembler::toResource);
    }

    public Long fetchUserIdByEmail(String email) {
        var opt = userQueryService.handle(new GetUserByEmailQuery(new EmailAddress(email)));
        return opt.map(e -> e.getId()).orElse(0L);
    }

    public boolean existsUserByEmailAndIdIsNot(String email, Long id) {
        var opt = userQueryService.handle(new GetUserByEmailQuery(new EmailAddress(email)));
        return opt.map(e -> !e.getId().equals(id)).orElse(false);
    }

    public Long createUser(String name, String email, String password,
                           String photo, int age, String location) {

        var cmd = new RegisterUserCommand(
                name,
                new EmailAddress(email).address(),
                password,
                photo,
                age,
                location
        );
        return userCommandService.handle(cmd);
    }

    public Long updateUser(Long userId, String name, int age, String location, String photo) {
        var exists = userQueryService.handle(new GetUserByIdQuery(userId)).isPresent();
        if (!exists) return 0L;
        userCommandService.handle(new UpdateUserProfileCommand(userId, name, age, location, photo));
        return userId;
    }

    public void deleteUser(Long userId) {
        userCommandService.handle(new DeleteUserAccountCommand(userId));
    }

    // Opcionales si los necesitas en este façade
    public void login(Long userId) {
        userCommandService.handle(new LoginUserCommand(userId));
    }

    public void logout(Long userId) {
        userCommandService.handle(new LogoutUserCommand(userId));
    }

    public List<UserResponse> fetchAllUsers() {
        return userQueryService.handle(new GetAllUsersQuery())
                .stream()
                .map(UserResourceFromEntityAssembler::toResource)
                .toList();
    }
}
