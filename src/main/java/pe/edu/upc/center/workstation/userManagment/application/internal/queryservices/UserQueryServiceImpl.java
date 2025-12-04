package pe.edu.upc.center.workstation.userManagment.application.internal.queryservices;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserQueryService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.user.*;

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

    @Override
    public Optional<User> handle(LoginUserQuery query) {
        var email = new EmailAddress(query.email());
        var userOpt = userRepository.findByEmail(email);

        if (userOpt.isEmpty()) return Optional.empty();

        var user = userOpt.get();

        if (!passwordMatches(query.password(), user.getPassword())) {
            return Optional.empty();
        }

        return Optional.of(user);
    }

    private boolean passwordMatches(String rawPassword, String hashedPassword) {
        return org.springframework.security.crypto.bcrypt.BCrypt.checkpw(rawPassword, hashedPassword);
    }
}
