package pe.edu.upc.center.workstation.properties.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.workstation.properties.domain.model.entities.Service;
import pe.edu.upc.center.workstation.properties.domain.model.valueobjects.Location;
import pe.edu.upc.center.workstation.properties.domain.model.valueobjects.OwnerId;
import pe.edu.upc.center.workstation.properties.domain.model.valueobjects.SpaceType;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity
@Table(name = "spaces")
public class Space extends AuditableAbstractAggregateRoot<Space> {

    @NotBlank
    @Column(name = "name", length = 100, nullable = false)
    private String name;

    @Embedded
    @NotNull
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "owner_id", nullable = false))
    })
    private OwnerId ownerId;

    @Embedded
    @NotNull
    private SpaceType spaceType;

    @NotNull
    @Min(0)
    @Column(name = "capacity", nullable = false)
    private Integer capacity;

    @NotNull
    @Min(0)
    @Column(name = "price", nullable = false)
    private Double price;

    @Column(name = "description", length = 300)
    private String description;

    @NotNull
    @Column(name = "available", nullable = false)
    private Boolean available;

    @Embedded
    @NotNull
    private Location location;

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
