package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record RemoveSpaceForOwnerCommand(Long ownerId, Long spaceId) {
    public RemoveSpaceForOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[RemoveSpaceForOwnerCommand] ownerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[RemoveSpaceForOwnerCommand] spaceId must be > 0");
    }
}
