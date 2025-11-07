package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

public record UpdateSpaceResource(Long spaceId, String name, SpaceType spaceType, Integer capacity, Double price, String description, Location location) {
}
