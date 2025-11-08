package pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.CreateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.commands.UpdateSpaceCommand;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Address;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

/**
 * Represent a space in the system.
 */

@Entity
@Table(name = "spaces")
public class Space extends AuditableAbstractAggregateRoot<Space> {

    @Getter
    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "ownerId",
                    column = @Column(name = "owner_id", nullable = false))
    })
    private  OwnerId ownerId;

    @Getter
    @Column(name = "space_type", length = 50, nullable = false)
    private String spaceType;

    @Getter
    @Min(0)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Getter
    @Min(0)
    @Column(name = "price", nullable = false)
    private Double price;

    @Getter
    @Column(name = "description", length = 300, nullable = false)
    private String description;

    @Getter
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "street",
                    column = @Column(name = "address_street", length = 100, nullable = false)),
            @AttributeOverride(name = "number",
                    column = @Column(name = "address_number", length = 5, nullable = false)),
            @AttributeOverride(name = "city",
                    column = @Column(name = "address_city",length = 20, nullable = false)),
            @AttributeOverride(name = "postalCode",
                    column = @Column(name = "address_postal_code",length = 20, nullable = false))
    })
    private Address address;


    /**
     * Default constructor for JPA.
     */
    protected Space() {}

    /**
     * Constructs a Profile instance from a CreateProfileCommand.
     *
     * @param command createProfileCommand containing profile details
     */
    public Space(CreateSpaceCommand command) {
        this.name = command.name();
        this.ownerId = command.ownerId();
        this.spaceType = command.spaceType();
        this.price = command.price();
        this.capacity = command.capacity();
        this.description = command.description();
        this.available = true;
        this.address = command.address();
    }

    /**
     * Update the space with the specified details.
     *
     * @param command the UpdateProfileCommand containing the new profile details.
     */
    public void updateSpace(UpdateSpaceCommand command) {
        this.name = command.name();
        this.spaceType = command.spaceType();
        this.capacity = command.capacity();
        this.price = command.price();
        this.description = command.description();
        this.address = command.address();
    }

    public String getFullAddress() {
        return address.getFullAddress();
    }

}
