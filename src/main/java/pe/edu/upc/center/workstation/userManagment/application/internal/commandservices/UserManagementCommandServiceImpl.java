package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserManagementRepository;

import java.util.Optional;

@Service
public class UserManagementCommandServiceImpl implements UserCommandService {

    private final UserManagementRepository userManagementRepository;

    public UserManagementCommandServiceImpl(UserManagementRepository userManagementRepository) {
        this.userManagementRepository = userManagementRepository;
    }

    @Override
    @Transactional
    public Long handle(CreateUserCommand createUserCommand) {
        var out = this.handle(new RegisterUserCommand(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.password(),
                createUserCommand.photo(),
                createUserCommand.age(),
                createUserCommand.location(),
                createUserCommand.roleName()
        ));
        return out.map(User::getId).orElse(0L);
    }

    @Override
    @Transactional
    public Optional<User> handle(RegisterUserCommand command) {
        var email = new EmailAddress(command.email());
        if (userManagementRepository.existsByEmail(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }

        var user = new User(
                command.name(),
                email.address(),
                command.password(),
                command.photo(),
                command.age(),
                command.location(),
                command.roleName()
        );

        try {
            userManagementRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while saving user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Optional<User> handle(UpdateUserProfileCommand command) {
        var user = userManagementRepository.findById(command.userId())
                .orElseThrow(() -> new NotFoundIdException(User.class, command.userId()));

        user.updateProfile(command.name(), command.age(), command.location(), command.photo());

        try {
            return Optional.of(userManagementRepository.save(user));
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while updating user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(SetUserRoleCommand command) {
        var user = userManagementRepository.findById(command.userId())
                .orElseThrow(() -> new NotFoundIdException(User.class, command.userId()));

        user.assignRole(command.roleName());
        userManagementRepository.save(user);
    }


}
