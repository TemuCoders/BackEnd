package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;

import jakarta.persistence.Embeddable;
import java.util.Objects;

@Embeddable
public record EmailAddress(String address) {
    public EmailAddress {

        if (Objects.isNull(address) || address.isBlank())
            throw new IllegalArgumentException("[EmailAddress] Email address cannot be null or blank");
        if (!address.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}$"))
            throw new IllegalArgumentException("[EmailAddress] Invalid email address : "+address);
    }
}