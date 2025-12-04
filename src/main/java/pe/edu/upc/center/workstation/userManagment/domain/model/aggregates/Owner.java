package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;

import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "owners")
public class Owner extends AuditableAbstractAggregateRoot<Owner> {

    @Getter
    @NotNull
    @Column(name = "user_id", nullable = false, unique = true)
    private Long userId;

    @Getter
    @NotBlank
    @Column(name = "company", length = 120, nullable = false)
    private String company;

    @Getter
    @NotBlank
    @Column(name = "ruc", length = 20, nullable = false, unique = true)
    private String ruc;

    @Getter
    @ElementCollection
    @CollectionTable(name = "owner_registered_spaces", joinColumns = @JoinColumn(name = "owner_id"))
    @Column(name = "space_id", nullable = false)
    private Set<Long> registeredSpaceIds = new LinkedHashSet<>();

    protected Owner() {}

    public Owner(Long userId, String company, String ruc) {
        setUserId(userId);
        setCompany(company);
        setRuc(ruc);
    }

    public void update(String company, String ruc) {
        setCompany(company);
        setRuc(ruc);
    }

    public void registerSpace(Long spaceId) {
        validateSpaceId(spaceId);
        registeredSpaceIds.add(spaceId);
    }

    public void removeSpace(Long spaceId) {
        validateSpaceId(spaceId);
        registeredSpaceIds.remove(spaceId);
    }

    private void setUserId(Long userId) {
        if (userId == null || userId <= 0) throw new IllegalArgumentException("userId must be > 0");
        this.userId = userId;
    }

    private void setCompany(String company) {
        if (company == null || company.isBlank()) throw new IllegalArgumentException("company must not be blank");
        this.company = company.trim();
    }

    private void setRuc(String ruc) {
        if (ruc == null || ruc.isBlank()) throw new IllegalArgumentException("ruc must not be blank");
        this.ruc = ruc.trim();
    }

    private void validateSpaceId(Long spaceId) {
        if (spaceId == null || spaceId <= 0) throw new IllegalArgumentException("spaceId must be > 0");
    }
}