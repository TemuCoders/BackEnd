package pe.edu.upc.center.workstation.userManagment.domain.services;

import pe.edu.upc.center.workstation.userManagment.domain.model.aggregates.Owner;
import pe.edu.upc.center.workstation.userManagment.domain.model.queries.owner.*;

import java.util.*;

public interface OwnerQueryService {
    List<Owner> handle(GetAllOwnersQuery query);
    Optional<Owner> handle(GetOwnerByIdQuery query);
    Optional<Owner> handle(GetOwnerByRucQuery query);
}
