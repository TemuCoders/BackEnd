package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.shared.utils.Util;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;

import java.util.Objects;

public record CreateUserCommand(
        String name,
        EmailAddress email,
        String password,
        String photo,
        int age,
        String location
) {
    public CreateUserCommand {
        Objects.requireNonNull(name, "[CreateUserCommand] name must not be null");
        Objects.requireNonNull(email, "[CreateUserCommand] email must not be null");
        Objects.requireNonNull(password, "[CreateUserCommand] password must not be null");
        Objects.requireNonNull(photo, "[CreateUserCommand] photo must not be null");
        Objects.requireNonNull(location, "[CreateUserCommand] location must not be null");

        if (name.isBlank()) throw new IllegalArgumentException("[CreateUserCommand] name is blank");
        if (password.isBlank()) throw new IllegalArgumentException("[CreateUserCommand] password is blank");
        if (photo.isBlank()) throw new IllegalArgumentException("[CreateUserCommand] photo is blank");
        if (location.isBlank()) throw new IllegalArgumentException("[CreateUserCommand] location is blank");

        if (age < Util.MIN_AGE || age > Util.MAX_AGE) {
            throw new IllegalArgumentException(
                    "[CreateUserCommand] age out of range: " + age);
        }
    }
}
