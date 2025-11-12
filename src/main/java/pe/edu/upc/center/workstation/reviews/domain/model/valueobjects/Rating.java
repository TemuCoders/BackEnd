package pe.edu.upc.center.workstation.reviews.domain.model.valueobjects;

import jakarta.persistence.Embeddable;

/**
 * Value Object que representa la calificación de una reseña (1-5).
 */
@Embeddable
public record Rating(int value) {

    public Rating {
        if (value < 1 || value > 5) {
            throw new IllegalArgumentException(
                    "Rating must be between 1 and 5, received: " + value
            );
        }
    }

    public Rating() {
        this(1);
    }
}