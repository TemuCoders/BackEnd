package pe.edu.upc.center.workstation.userManagment.interfaces.rest.transform.owners;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.OwnerResource;

import java.util.List;

public final class OwnerResourceFromEntityAssembler {
    private OwnerResourceFromEntityAssembler() {}
    public static OwnerResource toResource(Owner e) {
        return new OwnerResource(
                e.getId(),
                e.getCompany(),
                e.getRuc(),
                e.getRegisteredSpaceIds()
        );
    }
    public static List<OwnerResource> toResourceList(List<Owner> entities) {
        return entities.stream().map(OwnerResourceFromEntityAssembler::toResource).toList();
    }
}
