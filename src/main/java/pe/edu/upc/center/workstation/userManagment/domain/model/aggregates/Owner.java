package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.SpaceId;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner extends AuditableAbstractAggregateRoot<Owner> {

    @Getter
    @NotNull @NotBlank
    @Column(name = "company", length = 50, nullable = false)
    private String company;

    @Getter
    @NotNull @NotBlank
    @Column(name = "ruc", length = 30, nullable = false)
    private String ruc;

    @ElementCollection
    @CollectionTable(name = "owner_registered_spaces",
            joinColumns = @JoinColumn(name = "owner_id"))
    private final Set<SpaceId> registeredSpaces = new LinkedHashSet<>();

    protected Owner() { }

    public Owner(String company, String ruc) {
        this.company = company;
        this.ruc = ruc;
    }

    public void registerSpace(Long rawSpaceId) {
        var newId = new SpaceId(rawSpaceId);
        if (registeredSpaces.contains(newId))
            throw new IllegalArgumentException("Space already registered for this owner");
        registeredSpaces.add(newId);
    }

    public void removeSpace(Long rawSpaceId) {
        registeredSpaces.remove(new SpaceId(rawSpaceId));
    }

    public List<Long> getRegisteredSpaceIds() {
        return registeredSpaces.stream().map(SpaceId::value).toList();
    }
}
