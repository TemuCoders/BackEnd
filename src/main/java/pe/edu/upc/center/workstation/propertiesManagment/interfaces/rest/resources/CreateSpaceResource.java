package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

public record CreateSpaceResource(
        String name,
        SpaceType spaceType,
        Long ownerId,
        Double price,
        Integer capacity,
        String description,
        Boolean available,
        Location location
) {
}
