package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.commands.owner.*;


import java.util.Optional;

public interface OwnerCommandService {
    Long handle(CreateOwnerCommand command);
    Optional<Owner> handle(UpdateOwnerCommand command);
    void handle(DeleteOwnerCommand command);
    void handle(RegisterSpaceToOwnerCommand command);
    void handle(RemoveSpaceFromOwnerCommand command);
    void handle(RegisterSpaceForOwnerCommand cmd);
    void handle(RemoveSpaceForOwnerCommand cmd);
}
