package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

public record UpdateSpaceCommand(Long spaceId, String name, SpaceType spaceType, Integer capacity, Double price, String description, Location location) {
}
