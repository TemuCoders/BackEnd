package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;
import jakarta.persistence.*;


@Embeddable
public record UserRoleAssignment(
@Enumerated(EnumType.STRING)

@Column(name = "role_name")
UserRoleName roleName,

@Column(name = "role_id")
Long roleId) {
    public UserRoleAssignment() { this(null, null); }

    public boolean isEmpty() { return roleName == null || roleId == null || roleId <= 0; }

}
