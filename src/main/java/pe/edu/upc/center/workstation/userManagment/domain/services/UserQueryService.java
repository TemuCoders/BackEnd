package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;

import java.util.List;
import java.util.Optional;

public interface UserQueryService {
    Optional<User> handle(GetUserByIdQuery query);
    Optional<User> handle(GetUserByEmailQuery query);
    List<User> handle(GetAllUsersQuery query);
    List<User> handle(GetUsersByRoleNameQuery query);
    Optional<User> handle(LoginUserQuery query);
}
