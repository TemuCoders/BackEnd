package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;

public record RegisterSpaceForOwnerCommand(Long ownerId, Long spaceId) {}