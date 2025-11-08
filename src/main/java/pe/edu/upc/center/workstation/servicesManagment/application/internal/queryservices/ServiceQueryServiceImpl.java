package pe.edu.upc.center.workstation.servicesManagment.application.internal.queryservices;

import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetAllServicesQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceBySpaceIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceQueryService;
import pe.edu.upc.center.workstation.servicesManagment.infraestructure.persistense.jpa.repositories.ServiceRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Service
public class ServiceQueryServiceImpl  implements ServiceQueryService {
    private final ServiceRepository serviceRepository;
    public ServiceQueryServiceImpl(ServiceRepository serviceRepository) {
        this.serviceRepository = serviceRepository;
    }

    @Override
    public List<Service> handle(GetAllServicesQuery query){
        return this.serviceRepository.findAll();
    }

    @Override
    public Optional<Service> handle(GetServiceByIdQuery query){
        return serviceRepository.findById(query.serviceId());
    }

    @Override
    public List<Service> handle(GetServiceBySpaceIdQuery query){
        return serviceRepository.findBySpaceId(query.spaceId());
    }
}
