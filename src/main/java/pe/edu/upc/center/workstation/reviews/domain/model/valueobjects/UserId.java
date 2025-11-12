package pe.edu.upc.center.workstation.reviews.domain.model.valueobjects;

/**
 * Value Object que representa una referencia al Bounded Context de Users.
 * Reviews NO conoce la entidad User completa, solo su ID (Long).
 */
public record UserId(Long userId) {

    public UserId {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId must be positive");
        }
    }

    public UserId() {
        this(0L);
    }
}