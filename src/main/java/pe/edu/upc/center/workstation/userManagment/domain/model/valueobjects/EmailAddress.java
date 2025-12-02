package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/** Value Object para email del usuario (persistible y validable). */
@Embeddable
public record EmailAddress(@Email @NotBlank String address) {
    public EmailAddress() { this(null); }
}
