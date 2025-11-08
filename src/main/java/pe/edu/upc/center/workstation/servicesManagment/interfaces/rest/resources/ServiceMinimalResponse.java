package pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources;

import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;

public record ServiceMinimalResponse(
        Long serviceId,
        SpaceId spaceId,
        String name,
        String description,
        Double price
) {
}
