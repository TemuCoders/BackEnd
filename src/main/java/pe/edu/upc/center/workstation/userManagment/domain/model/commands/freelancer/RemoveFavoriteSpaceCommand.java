package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record RemoveFavoriteSpaceCommand(Long freelancerId, Long spaceId) {
    public RemoveFavoriteSpaceCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[RemoveFavoriteSpaceCommand] freelancerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[RemoveFavoriteSpaceCommand] spaceId must be > 0");
    }
}
