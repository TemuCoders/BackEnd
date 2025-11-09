package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.shared.utils.Util;

import java.util.Objects;

public record UpdateUserProfileCommand(Long userId, String name, int age, String location, String photo
) {
    public UpdateUserProfileCommand {
        Objects.requireNonNull(userId, "[UpdateUserProfileCommand] userId must not be null");
        if (userId <= 0) throw new IllegalArgumentException("[UpdateUserProfileCommand] userId must be > 0");

        if (Objects.isNull(name) || name.isBlank())
            throw new IllegalArgumentException("[UpdateUserProfileCommand] name must not be null or blank");
        if (age < Util.MIN_AGE || age > Util.MAX_AGE)
            throw new IllegalArgumentException(
                    String.format("[UpdateUserProfileCommand] age must be between %s and %s", Util.MIN_AGE, Util.MAX_AGE));
        if (Objects.isNull(location) || location.isBlank())
            throw new IllegalArgumentException("[UpdateUserProfileCommand] location must not be null or blank");
        if (Objects.isNull(photo) || photo.isBlank())
            throw new IllegalArgumentException("[UpdateUserProfileCommand] photo must not be null or blank");
    }
}
