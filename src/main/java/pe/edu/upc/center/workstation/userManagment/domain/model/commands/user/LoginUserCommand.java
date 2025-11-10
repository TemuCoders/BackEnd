package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import java.util.Objects;

public record LoginUserCommand(Long userId) {
    public LoginUserCommand {
        Objects.requireNonNull(userId, "[LoginUserCommand] userId must not be null");
        if (userId <= 0) throw new IllegalArgumentException("[LoginUserCommand] userId must be > 0");
    }
}
