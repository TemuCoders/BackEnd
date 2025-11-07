package pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects;

import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Access(AccessType.PROPERTY)
public record OwnerId(Long value) implements Serializable {
    public OwnerId {
        if (value==null)
            throw new NullPointerException("Owner id required");
    }

    @Column(name = "owner_id", nullable = false)
    @Override
    public Long value() {
        return value;
    }
}