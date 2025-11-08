package pe.edu.upc.center.workstation.servicesManagment.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Value Object representing the unique identifier of Space.
 *
 * This class is implemented as a Java Record, which means it is immutable by design.
 * It is also marked as @Embeddable so that JPA can embed it in entities.
 */
public record SpaceId(Long spaceId) {

    /**
     * Main constructor with validation
     *
     * @param spaceId the unique identifier for the space
     */
    @JsonCreator
    public SpaceId {
        if (Objects.isNull(spaceId) || spaceId < 0) {
            throw new IllegalArgumentException("SpaceId can't be null");
        }
    }

    @JsonValue
    public Long value() {
        return spaceId;
    }

    public SpaceId(){
        this(0L);
    }
}
