package pe.edu.upc.center.workstation.reviewsManagement.domain.services;

import pe.edu.upc.center.workstation.reviewsManagement.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviewsManagement.domain.model.queries.*;
import org.springframework.data.domain.Page;

import java.util.List;
import java.util.Optional;

public interface ReviewQueryService {
    List<Review> handle(GetAllReviewsQuery query);
    Optional<Review> handle(GetReviewByIdQuery query);
    Page<Review> handle(GetReviewsBySpaceQuery query);
    List<Review> handle(GetReviewsByUserQuery query);
    ReviewSummary handle(GetReviewsSummaryBySpaceQuery query);

    record ReviewSummary(double averageRating, long totalCount) {}
}