package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

/**
 * Response DTO for minimal space information.
 * @param spaceId
 * @param ownerId
 * @param spaceType
 * @param capacity
 * @param price
 * @param description
 * @param available
 * @param address
 */
public record  SpaceMinimalResponse(Long spaceId, String name,OwnerId ownerId, String spaceType, Integer capacity, Double price, String description, Boolean available, String address)
{ }