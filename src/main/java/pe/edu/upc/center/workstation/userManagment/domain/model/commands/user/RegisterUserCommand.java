package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.shared.utils.Util;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.*;

import java.util.Objects;


public record RegisterUserCommand(String name, String email, String password, String photo, int age, String location
) {
    public RegisterUserCommand {
        if (Objects.isNull(name) || name.isBlank())
            throw new IllegalArgumentException("[RegisterUserCommand] name must not be null or blank");
        if (Objects.isNull(password) || password.isBlank())
            throw new IllegalArgumentException("[RegisterUserCommand] password must not be null or blank");
        if (password.length() < 8)
            throw new IllegalArgumentException("[RegisterUserCommand] password must be at least 8 characters");
        if (Objects.isNull(photo) || photo.isBlank())
            throw new IllegalArgumentException("[RegisterUserCommand] photo must not be null or blank");
        if (age < Util.MIN_AGE || age > Util.MAX_AGE)
            throw new IllegalArgumentException(
                    String.format("[RegisterUserCommand] age must be between %s and %s", Util.MIN_AGE, Util.MAX_AGE));
        if (Objects.isNull(location) || location.isBlank())
            throw new IllegalArgumentException("[RegisterUserCommand] location must not be null or blank");
    }
}
