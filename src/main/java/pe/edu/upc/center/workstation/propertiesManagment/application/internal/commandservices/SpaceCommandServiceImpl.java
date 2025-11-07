package pe.edu.upc.center.workstation.propertiesManagment.application.internal.commandservices;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.infrastructure.persistence.jpa.repositories.SpaceRepository;

@Service
public class SpaceCommandServiceImpl implements SpaceCommandService {

    private final SpaceRepository spaceRepository;

    public SpaceCommandServiceImpl(SpaceRepository spaceRepository) {
        this.spaceRepository = spaceRepository;
    }

    @Override
    public Long handle(CreateSpaceCommand command) {
        var space = new Space(command.name(), command.spaceType(), command.ownerId(),command.price(),command.capacity(), command.description(),command.available());
        try {
            spaceRepository.save(space);
        }  catch (Exception ex) {
            throw new IllegalArgumentException("Error while saving space " +  ex.getMessage());
        }
        return space.getId();
    }
}
