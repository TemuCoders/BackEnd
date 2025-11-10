package pe.edu.upc.center.workstation.userManagment.domain.model.aggregates;

import pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects.EmailAddress;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;


import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.Getter;

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

    protected User() {}

    public User(String name, String email, String password, String photo, int age, String location) {
        this.name = name;
        this.email = new EmailAddress(email);
        this.password = password;
        this.photo = photo;
        this.age = age;
        this.location = location;
        this.registerDate = new Date();
    }

    public void register() { this.registerDate = new Date(); }

    public void login() { }

    public void logout() { }

    public void updateProfile(String name, int age, String location, String photo) {
        this.name = name;
        this.age = age;
        this.location = location;
        this.photo = photo;
    }

    public void deleteAccount() { }

    public String getPassword() { return password; }
    public void changePassword(String newPassword) { this.password = newPassword; }
}
