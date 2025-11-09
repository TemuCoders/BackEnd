package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.workstation.shared.utils.Util;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;


import java.util.*;

@Entity
@Table(
        name = "owners",
        uniqueConstraints = {
                @UniqueConstraint(name = "uk_owner_ruc", columnNames = "ruc")
        }
)
public class Owner extends AuditableAbstractAggregateRoot<Owner> {

    @Getter
    @NotNull @NotBlank
    @Column(name = "company", length = 50, nullable = false)
    private String company;

    @Getter
    @NotNull @NotBlank
    @Size(min = Util.RUC_LENGTH, max = Util.RUC_LENGTH)
    @Column(name = "ruc", length = 11, nullable = false, unique = true)
    private String ruc;

    @ElementCollection
    @CollectionTable(name = "owner_registered_spaces", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "space_id", nullable = false)
    private final Set<Long> registeredSpaceIds = new LinkedHashSet<>();

    protected Owner() { }

    public Owner(String company, String ruc) {
        this.company = company;
        this.ruc = ruc;
    }

    public void updateCompany(String company) {
        this.company = company;
    }

    public void update(String company, String ruc) {
        if (company == null || company.isBlank()) throw new IllegalArgumentException("company required");
        if (ruc == null || ruc.isBlank()) throw new IllegalArgumentException("ruc required");
        this.company = company.trim();
        this.ruc = ruc.trim();
    }

    public void updateRuc(String ruc) {
        this.ruc = ruc;
    }

    public void registerSpace(long spaceId) {
        if (spaceId <= 0) throw new IllegalArgumentException("invalid spaceId");
        if (!registeredSpaceIds.add(spaceId))
            throw new IllegalArgumentException("Space already registered for this owner");
    }

    public void removeSpace(long spaceId) {
        registeredSpaceIds.remove(spaceId);
    }

    public List<Long> getRegisteredSpaceIds() {
        return List.copyOf(registeredSpaceIds);
    }
}
