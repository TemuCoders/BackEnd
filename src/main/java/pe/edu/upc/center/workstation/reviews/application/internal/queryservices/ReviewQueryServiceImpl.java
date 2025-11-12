package pe.edu.upc.center.workstation.reviews.application.internal.queryservices;

import pe.edu.upc.center.workstation.reviews.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviews.domain.model.queries.*;
import pe.edu.upc.center.workstation.reviews.domain.model.valueobjects.ReviewStatus;
import pe.edu.upc.center.workstation.reviews.domain.services.ReviewQueryService;
import pe.edu.upc.center.workstation.reviews.infrastructure.persistence.jpa.repositories.ReviewRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class ReviewQueryServiceImpl implements ReviewQueryService {

    private final ReviewRepository reviewRepository;

    public ReviewQueryServiceImpl(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
    }

    @Override
    public List<Review> handle(GetAllReviewsQuery query) {
        return reviewRepository.findByStatus(ReviewStatus.PUBLISHED);
    }

    @Override
    public Optional<Review> handle(GetReviewByIdQuery query) {
        return reviewRepository.findById(query.reviewId());
    }

    @Override
    public Page<Review> handle(GetReviewsBySpaceQuery query) {
        var pageable = PageRequest.of(query.page(), query.size());
        return reviewRepository.findBySpaceId(query.spaceId(), pageable);
    }

    @Override
    public List<Review> handle(GetReviewsByUserQuery query) {
        return reviewRepository.findByUserId(query.userId());
    }

    @Override
    public ReviewSummary handle(GetReviewsSummaryBySpaceQuery query) {
        Double average = reviewRepository.calculateAverageRatingBySpace(query.spaceId());
        Long count = reviewRepository.countPublishedBySpace(query.spaceId());

        double averageRating = (average != null) ? average : 0.0;
        long totalCount = (count != null) ? count : 0L;

        return new ReviewSummary(averageRating, totalCount);
    }
}