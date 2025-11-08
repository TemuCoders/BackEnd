package pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.assemblers;

import pe.edu.upc.center.workstation.servicesManagment.domain.model.aggregates.Service;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.CreateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.commands.UpdateServiceCommand;
import pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects.SpaceId;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.CreateServiceRequest;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.ServiceMinimalResponse;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.ServiceResponse;
import pe.edu.upc.center.workstation.servicesManagment.interfaces.rest.resources.UpdatedServiceRequest;

public class ServiceAssembler {
    /**
     * Converts a CreateServiceRequest into a CreateServiceCommand.
     *
     * @param request the CreateServiceRequest
     * @return the corresponding CreateServiceCommand
     */
    public static CreateServiceCommand toCommandFromRequest(CreateServiceRequest request) {
        return new CreateServiceCommand(
                request.spaceId(),
                request.name(),
                request.description(),
                request.price()
        );
    }

    /**
     * Converts an UpdateServiceRequest into an UpdateServiceCommand.
     *
     * @param serviceId the ID of the service to update
     * @param request the UpdateServiceRequest
     * @return the corresponding UpdateServiceCommand
     */
    public static UpdateServiceCommand toCommandFromRequest(Long serviceId, UpdatedServiceRequest request) {
        return new UpdateServiceCommand(
                serviceId,
                request.name(),
                request.description(),
                request.price()
        );
    }

    /**
     * Converts a Service entity into a full ServiceResponse.
     *
     * @param entity the Service entity
     * @return the corresponding ServiceResponse
     */
    public static ServiceResponse toResponseFromEntity(Service entity) {
        return new ServiceResponse(
                entity.getId(),
                entity.getSpaceId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice()
        );
    }

    /**
     * Converts a Service entity into a minimal response (less fields).
     *
     * @param entity the Service entity
     * @return the corresponding ServiceMinimalResponse
     */
    public static ServiceMinimalResponse toResponseMinimalFromEntity(Service entity) {
        return new ServiceMinimalResponse(
                entity.getId(),
                entity.getSpaceId(),
                entity.getName(),
                entity.getDescription(),
                entity.getPrice()
        );
    }

    /**
     * Converts basic field values into a CreateServiceCommand.
     *
     * @param name service name
     * @param description service description
     * @param price service price
     * @param spaceId related SpaceId
     * @return the corresponding CreateServiceCommand
     */
    public static CreateServiceCommand toCommandFromValues(
            SpaceId spaceId,
            String name,
            String description,
            Double price
    ) {
        return new CreateServiceCommand(
                 spaceId,name,description, price
        );
    }

    /**
     * Converts basic field values into an UpdateServiceCommand.
     *
     * @param serviceId the service ID
     * @param name service name
     * @param description service description
     * @param price service price
     * @return the corresponding UpdateServiceCommand
     */
    public static UpdateServiceCommand toCommandFromValues(
            Long serviceId,
            String name,
            String description,
            Double price
    ) {
        return new UpdateServiceCommand(
                serviceId, name, description, price
        );
    }
}
