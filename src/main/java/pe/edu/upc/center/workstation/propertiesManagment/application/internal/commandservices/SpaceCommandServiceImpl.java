package pe.edu.upc.center.workstation.propertiesManagment.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories.SpaceRepository;

import java.util.Optional;

@Service
public class SpaceCommandServiceImpl implements SpaceCommandService {

    private final SpaceRepository spaceRepository;

    public SpaceCommandServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public Long handle(CreateSpaceCommand command) {
        var ownerId = new OwnerId(command.ownerId());

        var space = new Space(
                command.name(),
                ownerId,
                command.spaceType(),
                command.capacity(),
                command.price(),
                command.description(),
                command.available(),
                command.location()
        );

        try {
            spaceRepository.save(space);
        }  catch (Exception ex) {
            throw new IllegalArgumentException("Error while saving space " +  ex.getMessage());
        }
        return space.getId();
    }

    @Override
    public void handle(DeleteSpaceCommand command) {
        var spaceId = command.spaceId();

        if (!spaceRepository.existsById(spaceId)) {
            throw new IllegalArgumentException("Space with id " + spaceId + " does not exist");
        }

        try {
            spaceRepository.deleteById(command.spaceId());
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error while deleting space " + ex.getMessage());
        }
    }

    @Override
    public Optional<Space> handle(UpdateSpaceCommand command) {
        var spaceId = command.spaceId();

        if (!spaceRepository.existsById(spaceId)) {
            throw new IllegalArgumentException("Space with id " + spaceId + " does not exist");
        }

        var spaceToUpdate = spaceRepository.findById(spaceId).get();

        try {
            var updated = spaceRepository.save(spaceToUpdate);
            return Optional.of(updated);
        } catch (Exception ex) {
            throw new IllegalArgumentException("Error while updating space " + ex.getMessage());
        }
    }
}
