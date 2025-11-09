package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record DeleteOwnerCommand(Long ownerId) {
    public DeleteOwnerCommand {
        if (Objects.isNull(ownerId) || ownerId <= 0)
            throw new IllegalArgumentException("[DeleteOwnerCommand] ownerId must be > 0");
    }
}
