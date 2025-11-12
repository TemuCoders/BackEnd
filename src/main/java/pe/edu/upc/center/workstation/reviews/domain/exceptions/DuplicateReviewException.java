package pe.edu.upc.center.workstation.reviews.domain.exceptions;

/**
 * Excepción cuando un usuario intenta crear múltiples reviews para el mismo space.
 */
public class DuplicateReviewException extends RuntimeException {
    public DuplicateReviewException(Long userId, Long spaceId) {
        super("User " + userId + " already has an active review for space " + spaceId);
    }
}