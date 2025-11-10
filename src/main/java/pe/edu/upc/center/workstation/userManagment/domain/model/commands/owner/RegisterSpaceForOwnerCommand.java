package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record RegisterSpaceForOwnerCommand(Long ownerId, Long spaceId) {
    public RegisterSpaceForOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[RegisterSpaceForOwnerCommand] ownerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[RegisterSpaceForOwnerCommand] spaceId must be > 0");
    }
}