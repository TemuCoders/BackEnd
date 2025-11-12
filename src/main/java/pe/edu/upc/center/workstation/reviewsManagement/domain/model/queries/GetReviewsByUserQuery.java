package pe.edu.upc.center.workstation.reviewsManagement.domain.model.queries;

public record GetReviewsByUserQuery(Long userId) {
    public GetReviewsByUserQuery {
        if (userId == null || userId <= 0) {
            throw new IllegalArgumentException("UserId is required and must be positive");
        }
    }
}