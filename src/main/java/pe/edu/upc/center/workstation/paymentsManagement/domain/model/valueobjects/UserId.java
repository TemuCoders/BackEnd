package pe.edu.upc.center.workstation.paymentsManagement.domain.model.valueobjects;

/**
 * Value Object: referencia al Bounded Context de Users.
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