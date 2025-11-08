package pe.edu.upc.center.workstation.servicesManagment.domain.services;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceBySpaceIdQuery;

import java.util.List;
import java.util.Optional;

public interface ServiceQueryService {
    List<Service> handle(GetAllServicesQuery query);

    Optional<Service> handle(GetServiceByIdQuery query);

    List<Service> handle(GetServiceBySpaceIdQuery query);
}
