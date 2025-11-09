package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import java.util.Objects;

public record DeleteUserAccountCommand(Long userId) {
    public DeleteUserAccountCommand {
        Objects.requireNonNull(userId, "[DeleteUserAccountCommand] userId must not be null");
        if (userId <= 0) throw new IllegalArgumentException("[DeleteUserAccountCommand] userId must be > 0");
    }
}
