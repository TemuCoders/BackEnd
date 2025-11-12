package pe.edu.upc.center.workstation.reviews.domain.model.aggregates;

import pe.edu.upc.center.workstation.reviews.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.shared.domain.model.aggregates.AuditableAbstractAggregateRoot;
import jakarta.persistence.*;
import lombok.Getter;

/**
 * Aggregate Root: Review
 *
 * Representa una reseña de un usuario sobre un espacio.
 *
 * Invariantes:
 * - rating debe estar entre 1 y 5
 * - Solo puede existir una review activa por (userId, spaceId)
 * - comment no puede superar 2000 caracteres
 */
@Entity
@Table(name = "reviews")
public class Review extends AuditableAbstractAggregateRoot<Review> {

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "spaceId", column = @Column(name = "space_id", nullable = false))
    })
    private SpaceId spaceId;

    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "userId", column = @Column(name = "user_id", nullable = false))
    })
    private UserId userId;

    @Getter
    @Embedded
    @AttributeOverrides({
            @AttributeOverride(name = "value", column = @Column(name = "rating", nullable = false))
    })
    private Rating rating;

    @Getter
    @Column(name = "comment", length = 2000, nullable = false)
    private String comment;

    @Getter
    @Enumerated(EnumType.STRING)
    @Column(name = "status", length = 16, nullable = false)
    private ReviewStatus status;

    // Constructor vacío para JPA
    protected Review() {
    }

    // Constructor para crear nueva Review
    public Review(SpaceId spaceId, UserId userId, Rating rating, String comment) {
        super();
        this.spaceId = spaceId;
        this.userId = userId;
        this.rating = rating;
        this.comment = validateAndTrimComment(comment);
        this.status = ReviewStatus.PUBLISHED;
    }

    // Métodos de dominio
    public void update(Rating newRating, String newComment) {
        this.rating = newRating;
        this.comment = validateAndTrimComment(newComment);
    }

    public void delete() {
        this.status = ReviewStatus.DELETED;
    }

    private String validateAndTrimComment(String comment) {
        if (comment == null || comment.isBlank()) {
            throw new IllegalArgumentException("Comment cannot be empty");
        }
        String trimmed = comment.trim();
        if (trimmed.length() > 2000) {
            throw new IllegalArgumentException(
                    "Comment cannot exceed 2000 characters, received: " + trimmed.length()
            );
        }
        return trimmed;
    }

    // Getters
    public Long getSpaceId() {
        return spaceId.spaceId();
    }

    public Long getUserId() {
        return userId.userId();
    }

    public int getRatingValue() {
        return rating.value();
    }

    public boolean isActive() {
        return status == ReviewStatus.PUBLISHED;
    }
}