package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record RemoveSpaceFromOwnerCommand(Long ownerId, Long spaceId) {
    public RemoveSpaceFromOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[RemoveSpaceFromOwnerCommand] ownerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[RemoveSpaceFromOwnerCommand] spaceId must be > 0");
    }
}
