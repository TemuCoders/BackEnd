package pe.edu.upc.center.workstation.propertiesManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.entities.Service;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects.SpaceType;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;


@Entity
@Table(name = "spaces")
public class Space extends AuditableAbstractAggregateRoot<Space> {

    @Getter
    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Getter
    @Embedded
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "owner_id", nullable = false))
    })
    private OwnerId ownerId;

    @Getter
    @Embedded
    @NotNull
    private SpaceType spaceType;

    @Getter
    @NotNull
    @Min(0)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @Getter
    @NotNull
    @Min(0)
    @Column(name = "price", nullable = false)
    private Double price;

    @Getter
    @Column(name = "description", length = 300)
    private String description;

    @Getter
    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Getter
    @Embedded
    @NotNull
    private Location location;

    @Getter
    @ManyToMany
    @JoinTable(
            name = "space_services",
            joinColumns = @JoinColumn(name = "space_id"),
            inverseJoinColumns = @JoinColumn(name = "service_id")
    )
    private List<Service> services = new ArrayList<>();

    protected Space() {}

    public Space(String name, OwnerId ownerId, SpaceType spaceType,
                 Integer capacity, Double price, String description,
                 Boolean available, Location location) {
        this.name = name;
        this.ownerId = ownerId;
        this.spaceType = spaceType;
        this.capacity = capacity;
        this.price = price;
        this.description = description;
        this.available = available;
        this.location = location;
    }

}
