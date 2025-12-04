package pe.edu.upc.center.workstation.propertiesManagment.application.internal.commandservices;

import jakarta.persistence.PersistenceException;
import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories.SpaceRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;

import java.util.Optional;

/**
 * Implementation of the SpaceCommandService interface for handling space-related commands.
 *
 * <p>This service provides methods to create, update, and delete spaces entities, interacting
 *  * with the SpaceRepository.</p>
 *  */
@Service
public class SpaceCommandServiceImpl implements SpaceCommandService {

    private final SpaceRepository spaceRepository;

    /**
     * Constructs a SpaceCommandServiceImpl with the specified dependencies.
     *
     * @param spaceRepository the repository for managing Space entities
     */
    public SpaceCommandServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public Long handle(CreateSpaceCommand command) {
        var ownerId = command.ownerId();

        var space = new Space(command);
        try{
            var createdSpace = this.spaceRepository.save(space);
            return createdSpace.getId();
        } catch (Exception e){
            throw new PersistenceException("[SpaceCommandServiceImpl] Error while creating space" + e.getMessage());
        }
    }

    @Override
    public void handle(DeleteSpaceCommand command) {
        // If the profile does not exist, throw an exception
        if (!this.spaceRepository.existsById(command.spaceId())) {
            throw new NotFoundIdException(Space.class, command.spaceId());
        }

        // Try to delete the profile, if an error occurs, throw an exception
        try {
            spaceRepository.deleteById(command.spaceId());
        } catch (Exception ex) {
            throw new IllegalArgumentException("[SpaceCommandServiceImpl] Error while deleting space " + ex.getMessage());
        }
    }

    @Override
    public Optional<Space> handle(UpdateSpaceCommand command) {
        //Validate if the space exists
        var spaceId = command.spaceId();
        if (!spaceRepository.existsById(spaceId)) {
            throw new IllegalArgumentException("Space with id " + spaceId + " does not exist");
        }

        // Update the profile
        var spaceToUpdate = spaceRepository.findById(spaceId).get();
        spaceToUpdate.updateSpace(command);

        try {
            var updatedSpace = this.spaceRepository.save(spaceToUpdate);
            return Optional.of(updatedSpace);
        } catch (Exception ex) {
            throw new PersistenceException("[SpaceCommandServiceImpl] Error while updating space " + ex.getMessage());
        }
    }
}
