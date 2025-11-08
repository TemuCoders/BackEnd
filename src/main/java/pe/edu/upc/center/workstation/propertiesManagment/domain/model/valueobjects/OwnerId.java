package pe.edu.upc.center.workstation.propertiesManagment.domain.model.valueobjects;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;
import jakarta.persistence.Embeddable;

import java.util.Objects;

/**
 * Value Object representing the unique identifier of an Owner.
 *
 * This class is implemented as a Java Record, which means it is immutable by design.
 * It is also marked as @Embeddable so that JPA can embed it in entities.
 */
@Embeddable
public record OwnerId(Long ownerId) {

    /**
     * Main constructor with validation.
     *
     * The @JsonCreator annotation tells Jackson (used by Spring Boot)
     * that it can use this constructor to create an OwnerId instance from a JSON number.
     *
     * @param ownerId the unique identifier for the owner
     * @throws IllegalArgumentException if ownerId is null or negative
     */
    @JsonCreator
    public OwnerId {
        if (Objects.isNull(ownerId) || ownerId < 0) {
            throw new IllegalArgumentException("Owner Id must be greater than zero.");
        }
    }

    /**
     * This method controls how this record will be serialized back to JSON.
     *
     * The @JsonValue annotation means that when converting this object to JSON,
     * only the numeric value (ownerId) will be shown instead of an object.
     *
     * @return the numeric ownerId value
     */
    @JsonValue
    public Long value() {
        return ownerId;
    }

    /**
     * Default constructor required by JPA (and sometimes by Jackson).
     * Initializes the ownerId with a default value of 0L.
     *
     * You generally won't use this directly; it's mostly for frameworks.
     */
    public OwnerId() {
        this(0L);
    }
}
