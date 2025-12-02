package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.List;

/**
 * Response DTO for minimal space information.
 * @param spaceId the space ID
 * @param name the name of the space
 * @param ownerId the owner ID
 * @param spaceType the type of space
 * @param capacity the capacity of the space
 * @param price the price of the space
 * @param description the description of the space
 * @param available the availability status
 * @param address the full address as string
 * @param images the list of image URLs
 */
public record SpaceMinimalResponse(
        Long spaceId,
        String name,
        OwnerId ownerId,
        String spaceType,
        Integer capacity,
        Double price,
        String description,
        Boolean available,
        String address,
        List<String> images
) {
}