package pe.edu.upc.center.workstation.reviews.domain.model.queries;

public record GetReviewByIdQuery(Long reviewId) {
    public GetReviewByIdQuery {
        if (reviewId == null || reviewId <= 0) {
            throw new IllegalArgumentException("ReviewId is required and must be positive");
        }
    }
}