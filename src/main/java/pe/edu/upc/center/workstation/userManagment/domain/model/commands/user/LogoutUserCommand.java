package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import java.util.Objects;

public record LogoutUserCommand(Long userId) {
    public LogoutUserCommand {
        Objects.requireNonNull(userId, "[LogoutUserCommand] userId must not be null");
        if (userId <= 0) throw new IllegalArgumentException("[LogoutUserCommand] userId must be > 0");
    }
}
