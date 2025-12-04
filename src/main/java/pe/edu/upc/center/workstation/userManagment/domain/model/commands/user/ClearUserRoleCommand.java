package pe.edu.upc.center.workstation.userManagment.domain.model.commands.user;

public record ClearUserRoleCommand(Long userId) {
    public ClearUserRoleCommand {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId must be > 0");
    }
}
