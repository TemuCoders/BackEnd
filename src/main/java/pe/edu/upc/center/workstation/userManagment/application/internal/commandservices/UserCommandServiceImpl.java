package pe.edu.upc.center.workstation.userManagment.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.User;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.user.*;
import pe.edu.upc.center.workstation.userManagment.domain.services.UserCommandService;
import pe.edu.upc.center.workstation.userManagment.infrastructure.persistence.jpa.repositories.UserRepository;

import java.util.Optional;

@Service
public class UserCommandServiceImpl implements UserCommandService {

    private final UserRepository userRepository;

    public UserCommandServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Long handle(RegisterUserCommand command) {
        var email = command.email();
        if (this.userRepository.existsByEmail(email))
            throw new IllegalArgumentException("User with email " + email + " already exists");

        var user = new User(
                command.name(),
                command.email(),
                command.password(),
                command.photo(),
                command.age(),
                command.location()
        );

        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while saving user: " + e.getMessage());
        }
        return user.getId();
    }

    @Override
    public Optional<User> handle(UpdateUserProfileCommand command) {
        var userId = (long) command.userId();

        if (!this.userRepository.existsById(userId))
            throw new IllegalArgumentException("User with id " + command.userId() + " does not exist");

        var userToUpdate = this.userRepository.findById(userId).get();
        userToUpdate.updateProfile(command.name(), command.age(), command.location(), command.photo());

        try {
            var updatedUser = this.userRepository.save(userToUpdate);
            return Optional.of(updatedUser);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while updating user: " + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteUserAccountCommand command) {
        var userId = (long) command.userId();

        if (!this.userRepository.existsById(userId))
            throw new IllegalArgumentException("User with id " + command.userId() + " does not exist");

        try {
            this.userRepository.deleteById(userId);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while deleting user: " + e.getMessage());
        }
    }

    @Override
    public void handle(LoginUserCommand command) {
        var user = this.userRepository.findById((long) command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + command.userId() + " does not exist"));
        user.login();
        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while logging in user: " + e.getMessage());
        }
    }

    @Override
    public void handle(LogoutUserCommand command) {
        var user = this.userRepository.findById((long) command.userId())
                .orElseThrow(() -> new IllegalArgumentException("User with id " + command.userId() + " does not exist"));
        user.logout();
        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error while logging out user: " + e.getMessage());
        }
    }
}
