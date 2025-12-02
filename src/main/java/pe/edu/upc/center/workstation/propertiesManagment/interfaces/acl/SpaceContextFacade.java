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

import java.util.List;
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
        var getSpaceByIdQuery = new GetSpaceByIdQuery(spaceId);
        var optionalProfile = spaceQueryService.handle(getSpaceByIdQuery);
        if(optionalProfile.isEmpty()){
            return Optional.empty();
        }
        var spaceResponse = SpaceAssembler.toResponseFromEntity(optionalProfile.get());
        return Optional.of(spaceResponse);
    }

    /**
     * Fetch all spaces linked to a given Space ID.
     *
     * @param ownerId the Owner ID
     * @return a list of spaces linked to that space
     */
    public List<SpaceResponse> fetchSpaxcesByOwnerId(OwnerId ownerId) {
        var getSpaceByOwnerQuery = new GetSpaceByOwnerQuery(ownerId);
        var spaces = spaceQueryService.handle(getSpaceByOwnerQuery);
        return spaces.stream()
                .map(SpaceAssembler::toResponseFromEntity)
                .toList();
    }

    /**
     * Create a new space.
     *
     * @param name the name of the space
     * @param ownerId the owner ID
     * @param spaceType the type of space
     * @param price the price of the space
     * @param capacity the capacity of the space
     * @param description the description of the space
     * @param available the availability status
     * @param street the street name
     * @param streetNumber the street number
     * @param city the city name
     * @param postalCode the postal code
     * @param images the list of image URLs
     * @return the ID of the newly created space
     */
    public Long createSpace(String name,
                            OwnerId ownerId,
                            String spaceType,
                            Double price,
                            Integer capacity,
                            String description,
                            Boolean available,
                            String street,
                            String streetNumber,
                            String city,
                            String postalCode,
                            List<String> images) {
        var createSpaceCommand = SpaceAssembler.toCommandFromValues(
                name, ownerId, spaceType, price, capacity, description,
                available, street, streetNumber, city, postalCode, images
        );
        var spaceId = spaceCommandService.handle(createSpaceCommand);
        if(Objects.isNull(spaceId)) {
            return 0L;
        }
        return spaceId;
    }

    /**
     * Update an existing space.
     *
     * @param spaceId the ID of the space to update
     * @param name the name of the space
     * @param ownerId the owner ID
     * @param spaceType the type of space
     * @param capacity the capacity of the space
     * @param price the price of the space
     * @param description the description of the space
     * @param available the availability status
     * @param street the street name
     * @param streetNumber the street number
     * @param city the city name
     * @param postalCode the postal code
     * @param images the list of image URLs
     * @return the ID of the updated space, or 0L if update failed
     */
    public Long updateSpace(Long spaceId,
                            String name,
                            OwnerId ownerId,
                            String spaceType,
                            Integer capacity,
                            Double price,
                            String description,
                            Boolean available,
                            String street,
                            String streetNumber,
                            String city,
                            String postalCode,
                            List<String> images) {
        var updateSpaceCommand = SpaceAssembler.toCommandFromValues(
                spaceId, name, ownerId, spaceType, price, capacity,
                description, available, street, streetNumber, city, postalCode, images
        );
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