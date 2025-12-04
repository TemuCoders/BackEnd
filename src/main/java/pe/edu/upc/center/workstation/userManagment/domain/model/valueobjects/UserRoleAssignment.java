package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;

import jakarta.persistence.*;
import lombok.Getter;

@Embeddable
public record UserRoleAssignment(
        @Enumerated(EnumType.STRING)
        @Column(name = "role_name")
        UserRoleName roleName
) {
    public UserRoleAssignment() { this(null); }
    public boolean isEmpty() { return roleName == null; }
}
