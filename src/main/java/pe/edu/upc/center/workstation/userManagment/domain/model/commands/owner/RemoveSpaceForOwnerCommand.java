package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record RemoveSpaceForOwnerCommand(Long ownerId, Long spaceId) {}
