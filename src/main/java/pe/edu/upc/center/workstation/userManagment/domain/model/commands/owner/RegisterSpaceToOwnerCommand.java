package pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner;

import java.util.Objects;


public record RegisterSpaceToOwnerCommand(Long ownerId, Long spaceId) {

}
