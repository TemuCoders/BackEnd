package pe.edu.upc.center.workstation.properties.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

@Embeddable
public record SpaceId(Long value) {
    public SpaceId {
        if (value == null || value <= 0) throw new IllegalArgumentException("SpaceId must be positive");
    }
}

