package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.transform;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceResource;

import java.util.List;

public final class SpaceResourceFromEntityAssembler {
    private SpaceResourceFromEntityAssembler() {}
    public static SpaceResource toResource(Space resource) {
        return new SpaceResource(
                resource.getId(),
                resource.getName(),
                resource.getSpaceType(),
                resource.getOwnerId(),
                resource.getPrice(),
                resource.getCapacity(),
                resource.getDescription(),
                resource.getAvailable(),
                resource.getLocation(),
                resource.getServices()
        );
    }

    public static List<SpaceResource> toResourceList(List<Space> entities) {
        return entities.stream().map(SpaceResourceFromEntityAssembler::toResource).toList();
    }
}
