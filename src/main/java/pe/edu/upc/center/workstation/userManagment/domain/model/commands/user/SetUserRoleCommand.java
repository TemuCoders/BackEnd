package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.UserRoleName;

public record SetUserRoleCommand(Long userId, UserRoleName roleName, Long roleId) {
    public SetUserRoleCommand {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId must be > 0");
        if (roleName == null) throw new IllegalArgumentException("roleName required");
        if (roleId == null || roleId <= 0) throw new IllegalArgumentException("roleId must be > 0");
    }

    public Long roleEntityId() {
        return roleId;
    }
}
