package pe.edu.upc.center.workstation.bookingManagement.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

import java.util.Objects;

@Embeddable
public record SpaceId(Long spaceId) {
    public SpaceId {
        if(Objects.isNull(spaceId) || spaceId <= 0){
            throw new IllegalArgumentException("Space ID cannot be null or negative.");
        }
    }

    public SpaceId(){ this(0L);}
}
