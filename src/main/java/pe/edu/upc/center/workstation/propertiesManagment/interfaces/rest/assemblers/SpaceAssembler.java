package pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.assemblers;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates.Space;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.CreateSpaceRequest;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.UpdateSpaceRequest;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceResponse;
import pe.edu.upc.center.workstation.propertiesManagment.interfaces.rest.resources.SpaceMinimalResponse;

public class SpaceAssembler {


    public static CreateSpaceCommand toCommandFromRequest(CreateSpaceRequest request) {
        return new CreateSpaceCommand(
                request.name(),
                request.ownerId(),
                request.spaceType(),
                request.price(),
                request.capacity(),
                request.description(),
                request.available(),
                new Address(
                        request.street(),
                        request.streetNumber(),
                        request.city(),
                        request.postalCode()
                ),
                request.img()
        );
    }

    public static UpdateSpaceCommand toCommandFromRequest(Long spaceId, UpdateSpaceRequest request) {
        return new UpdateSpaceCommand(
                spaceId,
                request.name(),
                request.ownerId(),
                request.spaceType(),
                request.capacity(),
                request.price(),
                request.description(),
                request.available(),
                new Address(
                        request.street(),
                        request.streetNumber(),
                        request.city(),
                        request.postalCode()
                ),
                request.img()
        );
    }

    public static SpaceResponse toResponseFromEntity(Space entity) {
        return new SpaceResponse(
                entity.getId(),
                entity.getName(),
                entity.getOwnerId(),
                entity.getSpaceType(),
                entity.getCapacity(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getAvailable(),
                entity.getAddress().street(),
                entity.getAddress().number(),
                entity.getAddress().city(),
                entity.getAddress().postalCode(),
                entity.getImg()
        );
    }

    public static SpaceMinimalResponse toResponseMinimalFromEntity(Space entity) {
        return new SpaceMinimalResponse(
                entity.getId(),
                entity.getName(),
                entity.getOwnerId(),
                entity.getSpaceType(),
                entity.getCapacity(),
                entity.getPrice(),
                entity.getDescription(),
                entity.getAvailable(),
                entity.getAddress().getFullAddress(),
                entity.getImg()
        );
    }

    public static CreateSpaceCommand toCommandFromValues(
            String name,
            OwnerId ownerId,
            String spaceType,
            Double price,
            Integer capacity,
            String description,
            Boolean available,
            String street,
            String streetNumber,
            String city,
            String postalCode,
            String img
    ) {
        return new CreateSpaceCommand(
                name, ownerId, spaceType, price, capacity, description, available,
                new Address(street, streetNumber, city, postalCode), img
        );
    }

    public static UpdateSpaceCommand toCommandFromValues(
            Long spaceId,
            String name,
            OwnerId ownerId,
            String spaceType,
            Double price,
            Integer capacity,
            String description,
            Boolean available,
            String street,
            String streetNumber,
            String city,
            String postalCode,
            String img
    ) {
        return new UpdateSpaceCommand(
                spaceId, name, ownerId, spaceType, capacity, price, description, available,
                new Address(street, streetNumber, city, postalCode), img
        );
    }
}
