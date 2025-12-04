package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.*;

import java.util.Date;

@Entity
@Table(name = "users")
public class User extends AuditableAbstractAggregateRoot<User> {

    @Getter
    @NotBlank
    @Column(name = "name", length = 80, nullable = false)
    private String name;

    @Getter
    @Embedded
    @AttributeOverride(name = "address",
            column = @Column(name = "email", length = 120, nullable = false, unique = true))
    private EmailAddress email;

    @NotBlank
    @Column(name = "password", length = 120, nullable = false)
    private String password;

    @Getter
    @NotBlank
    @Column(name = "photo", length = 255, nullable = false)
    private String photo;

    @Getter
    @Min(0) @Max(120)
    @Column(name = "age", columnDefinition = "smallint", nullable = false)
    private int age;

    @Getter
    @NotBlank
    @Column(name = "location", length = 120, nullable = false)
    private String location;

    @Getter
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "register_date", nullable = false)
    private Date registerDate;

    @Getter
    @Embedded
    private UserRoleAssignment role;

    protected User() {}

    public User(String name, String email, String password, String photo, int age, String location, UserRoleName roleName) {
        setName(name);
        this.email = new EmailAddress(email);
        setPassword(password);
        setPhoto(photo);
        setAge(age);
        setLocation(location);
        this.registerDate = new Date();
        this.role = new UserRoleAssignment(roleName);
    }

    public void register() { this.registerDate = new Date(); }

    public void updateProfile(String name, int age, String location, String photo) {
        setName(name);
        setAge(age);
        setLocation(location);
        setPhoto(photo);
    }

    public void assignRole(UserRoleName roleName) {
        if (roleName == null) throw new IllegalArgumentException("roleName must not be null");
        this.role = new UserRoleAssignment(roleName);
    }

    public void changePassword(String newPassword) { setPassword(newPassword); }

    public String getPassword() { return password; }

    private void setName(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name must not be blank");
        this.name = name.trim();
    }

    private void setPassword(String pwd) {
        if (pwd == null || pwd.isBlank()) throw new IllegalArgumentException("password must not be blank");
        this.password = pwd;
    }

    private void setPhoto(String photo) {
        if (photo == null || photo.isBlank()) throw new IllegalArgumentException("photo must not be blank");
        this.photo = photo.trim();
    }

    private void setAge(int age) {
        if (age < 0 || age > 120) throw new IllegalArgumentException("age out of range");
        this.age = age;
    }

    private void setLocation(String location) {
        if (location == null || location.isBlank()) throw new IllegalArgumentException("location must not be blank");
        this.location = location.trim();
    }


}
