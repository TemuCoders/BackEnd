package pe.edu.upc.center.workstation.userManagment.domain.model.valueobjects;


import jakarta.persistence.Access;
import jakarta.persistence.AccessType;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.io.Serializable;

@Embeddable
@Access(AccessType.PROPERTY)
public record SpaceId(Long value) implements Serializable {

    public SpaceId {
        if (value == null) throw new IllegalArgumentException("spaceId is required");
    }

    // anota el accessor, no el campo
    @Column(name = "space_id", nullable = false)
    @Override
    public Long value() { return value; }
}