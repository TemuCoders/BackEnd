package pe.edu.upc.center.workstation.propertiesManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.DeleteSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByIdQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByOwnerQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceCommandService;
import pe.edu.upc.center.workstation.propertiesManagment.domain.services.SpaceQueryService;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.assemblers.SpaceAssembler;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceResponse;

import java.util.Objects;
import java.util.Optional;

/**
 * Facade class that serves as an intermediary between REST layer and domain layer for managing Spaces.
 */
@Service
public class SpaceContextFacade {

    private final SpaceCommandService spaceCommandService;
    private final SpaceQueryService spaceQueryService;

    /**
     * Constructor for SpaceContextFacade
     *
     * @param spaceCommandService the service for handling space commands
     * @param spaceQueryService   the service for handling space queries
     */
    public SpaceContextFacade(SpaceCommandService spaceCommandService, SpaceQueryService spaceQueryService) {
        this.spaceCommandService = spaceCommandService;
        this.spaceQueryService = spaceQueryService;
    }

    /**
     * Fetch a single space by its ID.
     *
     * @param spaceId the ID of the space
     * @return an Optional containing the space resource if found
     */
    public Optional<SpaceResponse> fetchSpaceById(Long spaceId) {
        var getSpaceByIdQuery = new  GetSpaceByIdQuery(spaceId);
        var optionalProfile = spaceQueryService.handle(getSpaceByIdQuery);
        if(optionalProfile.isEmpty()){
            return Optional.empty();
        }
        var spaceResponse = SpaceAssembler.toResponseFromEntity(optionalProfile.get());
        return Optional.of(spaceResponse);
    }


    /**
     * Create a new space.
     *
     * @return the ID of the newly created space
     */
    public Long createSpace(String name,
                            OwnerId ownerId,
                            String spaceType,
                            Double price,
                            Integer capacity,
                            String description,
                            Boolean available,
                            String street, String streetNumber,
                            String city, String postalCode) {
        var createSpaceCommand = SpaceAssembler.toCommandFromValues(name, ownerId,spaceType,price,capacity,description,available,street,streetNumber,city,postalCode);
        var spaceId = spaceCommandService.handle(createSpaceCommand);
        if(Objects.isNull(spaceId)) {
            return 0L;
        }
        return spaceId;
    }

    /**
     * Update an existing space.
     *
     * @param spaceId  the ID of the space to update
     * @return an Optional containing the updated space resource if successful
     */
    public Long updateSpace(Long spaceId, String name, OwnerId ownerId, String spaceType, Integer capacity, Double price, String description, Boolean available, String street, String streetNumber,
                            String city, String postalCode) {
        var updateSpaceCommand = SpaceAssembler.toCommandFromValues(spaceId,name,ownerId,spaceType,price, capacity,description,available,street,streetNumber,city,postalCode);
        var optionalSpace = spaceCommandService.handle(updateSpaceCommand);
        if(optionalSpace.isEmpty()) {
            return 0L;
        }
        return optionalSpace.get().getId();
    }

    /**
     * Delete a space by its ID.
     *
     * @param spaceId the ID of the space to delete
     */
    public void deleteSpace(Long spaceId) {
        spaceCommandService.handle(new DeleteSpaceCommand(spaceId));
    }
}
