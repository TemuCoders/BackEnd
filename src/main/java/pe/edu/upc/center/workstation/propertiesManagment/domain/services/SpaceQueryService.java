package pe.edu.upc.center.workstation.propertiesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetAllSpacesQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByNameQuery;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.queries.GetSpaceByOwnerQuery;

import java.util.List;
import java.util.Optional;

public interface SpaceQueryService {
    List<Space> handle(GetAllSpacesQuery query);
    Optional<Space> handle(GetSpaceByNameQuery query);
    Optional<Space> handle(GetSpaceByOwnerQuery query);
}
