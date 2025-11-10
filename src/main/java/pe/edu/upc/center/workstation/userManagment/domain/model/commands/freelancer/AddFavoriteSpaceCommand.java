package pe.edu.upc.center.workstation.userManagment.domain.model.commands.freelancer;

import java.util.Objects;

public record AddFavoriteSpaceCommand(Long freelancerId, Long spaceId) {
    public AddFavoriteSpaceCommand {
        if (Objects.isNull(freelancerId) || freelancerId <= 0)
            throw new IllegalArgumentException("[AddFavoriteSpaceCommand] freelancerId must be > 0");
        if (Objects.isNull(spaceId) || spaceId <= 0)
            throw new IllegalArgumentException("[AddFavoriteSpaceCommand] spaceId must be > 0");
    }
}
