package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

public record UpdateSpaceCommand(String name, SpaceType spaceType) {
}
