package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.List;

/**
 * Resource representation of a space
 * @param spaceId the space ID
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
 */
public record SpaceResponse(
        Long spaceId,
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
        List<String> images
) {
}