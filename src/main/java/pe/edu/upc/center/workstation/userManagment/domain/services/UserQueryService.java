package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;

import java.util.*;

public interface UserQueryService {
    List<User> handle(GetAllUsersQuery q);
    Optional<User> handle(GetUserByIdQuery q);
    Optional<User> handle(GetUserByEmailQuery q);
}
