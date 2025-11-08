package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

/**
 * Resource representation of a space
 * @param spaceId
 * @param ownerId
 * @param spaceType
 * @param capacity
 * @param price
 * @param description
 * @param available
 * @param street
 * @param streetNumber
 * @param city
 * @param postalCode
 */
public record SpaceResponse(Long spaceId, String name,OwnerId ownerId, String spaceType, Integer capacity, Double price, String description, Boolean available, String street, String streetNumber,
                            String city, String postalCode)
{ }
