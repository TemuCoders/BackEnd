package pe.edu.upc.center.workstation.reviewsManagement.domain.model.valueobjects;

/**
 * Value Object que representa una referencia al Bounded Context de Spaces.
 * Reviews NO conoce la entidad Space completa, solo su ID (Long).
 */
public record SpaceId(Long spaceId) {

    public SpaceId {
        if (spaceId == null || spaceId <= 0) {
            throw new IllegalArgumentException("SpaceId must be positive");
        }
    }

    public SpaceId() {
        this(0L);
    }
}