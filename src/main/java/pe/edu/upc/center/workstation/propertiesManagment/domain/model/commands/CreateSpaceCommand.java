package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.Objects;

/**
 * Command to create a new Space
 *
 * @param name The name of the space.
 * @param ownerId The owner id of the space.
 * @param spaceType The type of space.
 * @param price The price of the space.
 * @param capacity The capacity of the space.
 * @param description The description of the space.
 * @param available The availability of the space.
 * @param address The address of the space
 */

public record CreateSpaceCommand(
        String name,
        OwnerId ownerId,
        String spaceType,
        Double price,
        Integer capacity,
        String description,
        Boolean available,
        Address address
) {
    public CreateSpaceCommand{
        Objects.requireNonNull(name, "[CreateProfileCommand] name must not be null");
        Objects.requireNonNull(ownerId, "[CreateProfileCommand] ownerId must not be null");
        Objects.requireNonNull(spaceType, "[CreateProfileCommand] spaceType must not be null");
        Objects.requireNonNull(price, "[CreateProfileCommand] price must not be null");
        Objects.requireNonNull(capacity, "[CreateProfileCommand] capacity must not be null");
        Objects.requireNonNull(description, "[CreateProfileCommand] description must not be null");
        Objects.requireNonNull(available, "[CreateProfileCommand] available must not be null");
        Objects.requireNonNull(address, "[CreateProfileCommand] address must not be null");

    }
}