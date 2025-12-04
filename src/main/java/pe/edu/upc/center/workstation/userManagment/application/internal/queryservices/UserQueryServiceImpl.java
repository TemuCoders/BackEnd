package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class UserQueryServiceImpl implements pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService {

    private final UserRepository userRepository;

    public UserQueryServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> handle(GetUserByIdQuery query) {
        return userRepository.findById(query.userId());
    }

    @Override
    public Optional<User> handle(GetUserByEmailQuery query) {
        return userRepository.findByEmail(new EmailAddress(query.email().address()));
    }

    @Override
    public List<User> handle(GetAllUsersQuery query) {
        return userRepository.findAll();
    }

    @Override
    public List<User> handle(GetUsersByRoleNameQuery query) {
        return userRepository.findByRole_RoleName(query.roleName());
    }
}
