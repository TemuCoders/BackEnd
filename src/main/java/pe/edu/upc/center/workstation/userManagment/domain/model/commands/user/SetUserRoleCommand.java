package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;
import java.util.Objects;

public record SetUserRoleCommand(Long userId, UserRoleName roleName) {
    public SetUserRoleCommand {
        Objects.requireNonNull(userId, "userId must not be null");
        if (userId <= 0) throw new IllegalArgumentException("userId must be > 0");
        Objects.requireNonNull(roleName, "roleName must not be null");
    }
}
