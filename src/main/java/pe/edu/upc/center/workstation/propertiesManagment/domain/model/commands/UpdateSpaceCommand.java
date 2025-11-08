package pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands;

import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;

import java.util.Objects;

/**
 * Command to update an existing space
 *
 * @param spaceId the ID of the space to update
 * @param name the name of space.
 * @param spaceType the type of the space.
 * @param capacity the capacity of the space.
 * @param price the price of the price.
 * @param description the description of the space.
 * @param address the address of the space.
 */

public record UpdateSpaceCommand(Long spaceId, String name, OwnerId ownerId,String spaceType, Integer capacity, Double price, String description, Boolean available,Address address) {

    public UpdateSpaceCommand {
        Objects.requireNonNull(name, "[UpdateSpaceCommand] name cannot be null]");
        Objects.requireNonNull(ownerId, "[UpdateSpaceCommand] ownerId cannot be null");
        Objects.requireNonNull(spaceType, "[UpdateSpaceCommand] spaceType cannot be null");
        Objects.requireNonNull(capacity, "[UpdateSpaceCommand] capacity cannot be null");
        Objects.requireNonNull(price, "[UpdateSpaceCommand] price cannot be null");
        Objects.requireNonNull(description, "[UpdateSpaceCommand] description cannot be null");
        Objects.requireNonNull(address, "[UpdateSpaceCommand] address cannot be null");

        if(spaceId < 0){
            throw new IllegalArgumentException("[UpdateSpaceCommand] spaceId cannot be negative");
        }
    }
}
