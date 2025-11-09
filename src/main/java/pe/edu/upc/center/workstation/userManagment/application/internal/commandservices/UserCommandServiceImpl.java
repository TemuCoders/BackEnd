package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.shared.domain.exceptions.*;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Long handle(RegisterUserCommand c) {
        var email = new EmailAddress(c.email());

        if (userRepository.existsByEmail(email))
            throw new IllegalArgumentException("[UserCommandServiceImpl] User with email "
                    + email + " already exists");

        var user = new User(
                c.name(),
                email.address(),
                c.password(),
                c.photo(),
                c.age(),
                c.location()
        );

        try {
            userRepository.save(user);
            return user.getId();
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while saving user: " + e.getMessage());
        }
    }

    @Override
    public Optional<User> handle(UpdateUserProfileCommand c) {
        var userId = c.userId();

        if (!userRepository.existsById(userId))
            throw new NotFoundIdException(User.class, userId);

        var user = userRepository.findById(userId).get();
        user.updateProfile(c.name(), c.age(), c.location(), c.photo());

        try {
            return Optional.of(userRepository.save(user));
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while updating user: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteUserAccountCommand c) {
        var userId = c.userId();

        if (!userRepository.existsById(userId))
            throw new NotFoundIdException(User.class, userId);

        try {
            userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while deleting user: " + e.getMessage());
        }
    }

    @Override
    public void handle(LoginUserCommand c) {
        var user = userRepository.findById(c.userId())
                .orElseThrow(() -> new NotFoundIdException(User.class, c.userId()));
        user.login();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while logging in user: " + e.getMessage());
        }
    }

    @Override
    public void handle(LogoutUserCommand c) {
        var user = userRepository.findById(c.userId())
                .orElseThrow(() -> new NotFoundIdException(User.class, c.userId()));
        user.logout();
        try {
            userRepository.save(user);
        } catch (Exception e) {
            throw new PersistenceException("[UserCommandServiceImpl] Error while logging out user: " + e.getMessage());
        }
    }

    @Override
    public Long handle(CreateUserCommand createUserCommand) {
        if (!createUserCommand.email().address().matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"))
            throw new IllegalArgumentException("[userCommandService] Invalid email address  ");
        var mapped = new RegisterUserCommand(
                createUserCommand.name(),
                new EmailAddress(createUserCommand.email().address()).address(),
                createUserCommand.password(),
                createUserCommand.photo(),
                createUserCommand.age(),
                createUserCommand.location()
        );
        return this.handle(mapped);
    }
}
