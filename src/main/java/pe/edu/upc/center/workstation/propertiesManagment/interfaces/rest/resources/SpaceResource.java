package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.entities.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;

import java.util.List;

public record SpaceResource(
        Long spaceId,
        String name,
        SpaceType spaceType,
        OwnerId ownerId,
        Double price,
        Integer capacity,
        String description,
        Boolean available,
        Location location,
        List<Service> services
) {
}
