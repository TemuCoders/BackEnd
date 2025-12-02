package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    /** Compatibilidad: adapta CreateUserCommand → RegisterUserCommand y retorna id. */
    @Override
    @Transactional
    public Long handle(CreateUserCommand createUserCommand) {
        var out = this.handle(new RegisterUserCommand(
                createUserCommand.name(),
                createUserCommand.email(),
                createUserCommand.password(),
                createUserCommand.photo(),
                createUserCommand.age(),
                createUserCommand.location()
        ));
        return out.map(User::getId).orElse(0L);
    }

    @Override
    @Transactional
    public Optional<User> handle(RegisterUserCommand command) {
        var email = new EmailAddress(command.email());
        if (userRepository.existsByEmailAddress(email)) {
            throw new IllegalArgumentException("User with email already exists");
        }
        var user = new User(
                command.name(),
                email,
                command.password(),
                command.photo(),
                command.age(),
                command.location()
        );
        try {
            userRepository.save(user);
            return Optional.of(user);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while saving user: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public Optional<User> handle(UpdateUserProfileCommand command) {
        var user = userRepository.findById(command.userId())
                .orElseThrow(() -> new NotFoundIdException(User.class, command.userId()));

        user.updateProfile(command.name(), command.age(), command.location(), command.photo());
        try {
            var updated = userRepository.save(user);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while updating user: " + e.getMessage());
        }
    }
}
