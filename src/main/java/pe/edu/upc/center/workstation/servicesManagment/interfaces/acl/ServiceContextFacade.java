package pe.edu.upc.center.workstation.servicesManagment.interfaces.acl;

import org.springframework.stereotype.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.DeleteServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceByIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.queries.GetServiceBySpaceIdQuery;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceCommandService;
import pe.edu.upc.center.workstation.servicesManagment.domain.services.ServiceQueryService;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.assemblers.ServiceAssembler;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.ServiceResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * Facade class that serves as an intermediary between REST layer and domain layer for managing Services.
 */
@Service
public class ServiceContextFacade {

    private final ServiceCommandService serviceCommandService;
    private final ServiceQueryService serviceQueryService;

    /**
     * Constructor for ServiceContextFacade
     *
     * @param serviceCommandService the service for handling service commands
     * @param serviceQueryService   the service for handling service queries
     */
    public ServiceContextFacade(ServiceCommandService serviceCommandService, ServiceQueryService serviceQueryService) {
        this.serviceCommandService = serviceCommandService;
        this.serviceQueryService = serviceQueryService;
    }

    /**
     * Fetch a single service by its ID.
     *
     * @param serviceId the ID of the service
     * @return an Optional containing the service resource if found
     */
    public Optional<ServiceResponse> fetchServiceById(Long serviceId) {
        var getServiceByIdQuery = new GetServiceByIdQuery(serviceId);
        var optionalService = serviceQueryService.handle(getServiceByIdQuery);
        if (optionalService.isEmpty()) {
            return Optional.empty();
        }
        var serviceResponse = ServiceAssembler.toResponseFromEntity(optionalService.get());
        return Optional.of(serviceResponse);
    }

    /**
     * Fetch all services linked to a given Space ID.
     *
     * @param spaceId the Space ID
     * @return a list of services linked to that space
     */
    public List<ServiceResponse> fetchServicesBySpaceId(SpaceId spaceId) {
        var getServicesBySpaceIdQuery = new GetServiceBySpaceIdQuery(spaceId);
        var services = serviceQueryService.handle(getServicesBySpaceIdQuery);
        return services.stream()
                .map(ServiceAssembler::toResponseFromEntity)
                .toList();
    }

    /**
     * Create a new service.
     *
     * @return the ID of the newly created service
     */
    public Long createService(SpaceId spaceId, String name, String description, Double price) {
        var createServiceCommand = ServiceAssembler.toCommandFromValues(spaceId, name, description, price);
        var serviceId = serviceCommandService.handle(createServiceCommand);
        if (Objects.isNull(serviceId)) {
            return 0L;
        }
        return serviceId;
    }

    /**
     * Update an existing service.
     *
     * @param serviceId the ID of the service to update
     * @return an Optional containing the updated service resource if successful
     */
    public Long updateService(Long serviceId, String name, String description, Double price) {
        var updateServiceCommand = ServiceAssembler.toCommandFromValues(serviceId, name, description, price);
        var optionalService = serviceCommandService.handle(updateServiceCommand);
        if (optionalService.isEmpty()) {
            return 0L;
        }
        return optionalService.get().getId();
    }

    /**
     * Delete a service by its ID.
     *
     * @param serviceId the ID of the service to delete
     */
    public void deleteService(Long serviceId) {
        serviceCommandService.handle(new DeleteServiceCommand(serviceId));
    }
}
