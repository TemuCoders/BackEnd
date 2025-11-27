package pe.edu.upc.center.workstation.userManagment.interfaces.rest.assemblers.owner;

import java.util.ArrayList;
import java.util.Objects;
import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.interfaces.rest.resources.owners.OwnerResponse;

public final class OwnerResourceFromEntityAssembler {
    private OwnerResourceFromEntityAssembler() {}

    public static OwnerResponse toResponseFromEntity(Owner entity) {
        Objects.requireNonNull(entity, "[OwnerResponseFromEntityAssembler] entity es null");
        var registeredSpaceIds = new ArrayList<>(entity.getRegisteredSpaceIds());
        return new OwnerResponse(
                entity.getId(),
                entity.getCompany(),
                entity.getRuc(),
                registeredSpaceIds
        );
    }
}
