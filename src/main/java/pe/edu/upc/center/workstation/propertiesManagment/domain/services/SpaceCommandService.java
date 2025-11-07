package pe.edu.upc.center.workstation.propertiesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;

import java.util.Optional;

public interface SpaceCommandService {
    Long handle(CreateSpaceCommand command);
    Optional<Space> handle(UpdateSpaceCommand command);
    void handle(DeleteSpaceCommand command);
}
