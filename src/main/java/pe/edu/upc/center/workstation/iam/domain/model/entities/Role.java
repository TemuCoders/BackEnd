package pe.edu.upc.center.workstation.iam.domain.model.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.With;
import pe.edu.upc.center.workstation.iam.domain.model.valueobjects.Roles;

import java.util.List;

/**
 * Role entity.
 *
 * <p>This entity represents the role of a user in the system.
 *     It is used to define the permissions of a user.
 * </p>
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@With
public class Role {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @Enumerated(EnumType.STRING)
  @Column(length = 20)
  private Roles name;

  /**
   * Constructor for Role.
   *
   * @param name the name of the role
   */
  public Role(Roles name) {
    this.name = name;
  }

  /**
   * Get the name of the role as a string.
   *
   * @return the name of the role as a string
   */
  public String getStringName() {
    return name.name();
  }

  /**
   * Get the default role.
   *
   * @return the default role
   */
  public static Role getDefaultRole() {
    return new Role(Roles.ROLE_USER);
  }

  /**
   * Get the role from its name.
   *
   * @param name the name of the role
   * @return the role
   */
  public static Role toRoleFromName(String name) {
    return new Role(Roles.valueOf(name));
  }

  /**
   * Validate a set of roles.
   * If the set is null or empty, it returns a list with the default role.
   *
   * @param roles the set of roles to validate
   * @return the validated set of roles
   */
  public static List<Role> validateRoleSet(List<Role> roles) {
    if (roles == null || roles.isEmpty()) {
      return List.of(getDefaultRole());
    }
    return roles;
  }
}
