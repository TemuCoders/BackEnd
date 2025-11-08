package pe.edu.upc.center.workstation.propertiesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;

import java.util.Optional;

/**
 * Service interface for handling command related to Space entities.
 */
public interface SpaceCommandService {
    /**
     * Handles the creation of a new space based on the provided command.
     * @param command the command containing the details of the student to be created
     * @return the details of the newly created space
     */
    Long handle(CreateSpaceCommand command);

    /**
     * Handles the update of the space based on the provided command.
     * @param command the command containing the details of the spaces to be updated.
     * @return an Optional containing the updated Space if the updated was successful,
     *  or an empty Optional if not
     */
    Optional<Space> handle(UpdateSpaceCommand command);

    /**
     * Hand,es the deletion of the student based on the provided command.
     * @param command the command containing the details of the student to be deleted.
     */
    void handle(DeleteSpaceCommand command);
}
