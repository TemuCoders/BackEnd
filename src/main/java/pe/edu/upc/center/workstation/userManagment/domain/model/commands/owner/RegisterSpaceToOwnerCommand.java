package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;


public record RegisterSpaceToOwnerCommand(Long ownerId, Long spaceId) {
    public RegisterSpaceToOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[RegisterSpaceToOwnerCommand] ownerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[RegisterSpaceToOwnerCommand] spaceId must be > 0");
    }
}
