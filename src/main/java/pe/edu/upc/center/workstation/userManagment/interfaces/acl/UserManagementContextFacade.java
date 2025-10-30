package pe.edu.upc.center.workstation.userManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.services.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.users.*;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.users.UserResourceFromEntityAssembler;

import java.util.*;

@Service
public class UserManagementContextFacade {
    private final UserCommandService userCommandService;
    private final UserQueryService userQueryService;

    public UserManagementContextFacade(UserCommandService userCommandService, UserQueryService _userQueryService) {
        this.userCommandService = userCommandService;
        this.userQueryService = _userQueryService;
    }


    public Optional<UserResource> fetchUserById(int userId) {
        var opt = userQueryService.handle(new GetUserByIdQuery(userId));
        return opt.map(UserResourceFromEntityAssembler::toResource);
    }

    public int fetchUserIdByEmail(String email) {
        var opt = userQueryService.handle(new GetUserByEmailQuery(email));
        return opt.map(e -> e.getId().intValue()).orElse(0);
    }

    public boolean existsUserByEmailAndIdIsNot(String email, int id) {
        var opt = userQueryService.handle(new GetUserByEmailQuery(email));
        return opt.map(e -> !e.getId().equals((long) id)).orElse(false);
    }


    public int createUser(String name, String email, String password,
                          String photo, int age, String location) {
        var id = userCommandService.handle(new RegisterUserCommand(name, email, password, photo, age, location));
        return id == null ? 0 : id.intValue();
    }

    public int updateUser(int userId, String name, int age, String location, String photo) {
        var opt = userQueryService.handle(new GetUserByIdQuery(userId));
        if (opt.isEmpty()) return 0;
        userCommandService.handle(new UpdateUserProfileCommand(userId, name, age, location, photo));
        return userId;
    }

    public void deleteUser(int userId) {
        userCommandService.handle(new DeleteUserAccountCommand(userId));
    }
}
