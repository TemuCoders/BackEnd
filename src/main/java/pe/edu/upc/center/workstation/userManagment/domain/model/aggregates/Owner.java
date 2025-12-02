package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(
        name = "owners",
        uniqueConstraints = @UniqueConstraint(name = "uk_owners_ruc", columnNames = "ruc")
)
public class Owner extends AuditableAbstractAggregateRoot<Owner> {

    @Getter
    @NotBlank
    @Column(name = "company", length = 120, nullable = false)
    private String company;

    @Getter
    @NotBlank
    @Column(name = "ruc", length = 11, nullable = false, unique = true)
    private String ruc;

    @Getter
    @ElementCollection
    @CollectionTable(name = "owner_registered_spaces", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "space_id", nullable = false)
    private Set<Long> registeredSpaceIds = new LinkedHashSet<>();

    protected Owner() { }

    public Owner(String company, String ruc) {
        setCompany(company);
        setRuc(ruc);
    }

    public void update(String company, String ruc) {
        setCompany(company);
        setRuc(ruc);
    }

    public void registerSpace(Long spaceId) {
        if (spaceId == null || spaceId <= 0) throw new IllegalArgumentException("spaceId must be > 0");
        registeredSpaceIds.add(spaceId);
    }

    public void removeSpace(Long spaceId) {
        if (spaceId == null || spaceId <= 0) throw new IllegalArgumentException("spaceId must be > 0");
        registeredSpaceIds.remove(spaceId);
    }

    private void setCompany(String company) {
        if (company == null || company.isBlank()) throw new IllegalArgumentException("company must not be blank");
        this.company = company.trim();
    }

    private void setRuc(String ruc) {
        if (ruc == null || !ruc.matches("\\d{11}")) throw new IllegalArgumentException("ruc must be 11 digits");
        this.ruc = ruc;
    }
}
