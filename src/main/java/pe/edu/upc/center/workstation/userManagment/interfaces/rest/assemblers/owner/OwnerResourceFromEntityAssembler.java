package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.OwnerResponse;

public final class OwnerResourceFromEntityAssembler {
    private OwnerResourceFromEntityAssembler() {}

    public static OwnerResponse toResource(Owner entity) {
        return new OwnerResponse(
                entity.getId(),
                entity.getCompany(),
                entity.getRuc(),
                entity.getRegisteredSpaceIds()
        );
    }
}
