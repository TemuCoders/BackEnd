package pe.edu.upc.center.workstation.reviewsManagement.application.internal.commandservices;

import org.springframework.beans.factory.annotation.Qualifier;
import pe.edu.upc.center.workstation.reviewsManagement.application.internal.outboundservices.acl.ExternalSpaceService;
import pe.edu.upc.center.workstation.reviewsManagement.application.internal.outboundservices.acl.ExternalUserService;
import pe.edu.upc.center.workstation.reviewsManagement.domain.exceptions.DuplicateReviewException;
import pe.edu.upc.center.workstation.reviewsManagement.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviewsManagement.domain.model.commands.*;
import pe.edu.upc.center.workstation.reviewsManagement.domain.model.valueobjects.*;
import pe.edu.upc.center.workstation.reviewsManagement.domain.services.ReviewCommandService;
import pe.edu.upc.center.workstation.reviewsManagement.infrastructure.persistence.jpa.repositories.ReviewRepository;
import pe.edu.upc.center.workstation.shared.domain.exceptions.NotFoundIdException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ReviewCommandServiceImpl implements ReviewCommandService {

    private final ReviewRepository reviewRepository;
    private final ExternalUserService externalUserService;
    private final ExternalSpaceService externalSpaceService;

    public ReviewCommandServiceImpl(
            ReviewRepository reviewRepository,
            @Qualifier("reviewsExternalUserService") ExternalUserService externalUserService,
            ExternalSpaceService externalSpaceService
    ) {
        this.reviewRepository = reviewRepository;
        this.externalUserService = externalUserService;
        this.externalSpaceService = externalSpaceService;
    }

    @Override
    @Transactional
    public Long handle(CreateReviewCommand command) {
        // Validar User existe
        externalUserService.validateUserExists(command.userId());

        // Validar Space existe
        externalSpaceService.validateSpaceExists(command.spaceId());

        // Validar no existe review activa
        var existingReview = reviewRepository.findActiveByUserAndSpace(
                command.userId(),
                command.spaceId()
        );

        if (existingReview.isPresent()) {
            throw new DuplicateReviewException(command.userId(), command.spaceId());
        }

        // Crear agregado
        var review = new Review(
                new SpaceId(command.spaceId()),
                new UserId(command.userId()),
                new Rating(command.rating()),
                command.comment()
        );

        // Persistir
        try {
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error saving review: " + e.getMessage());
        }

        return review.getId();
    }

    @Override
    @Transactional
    public Optional<Review> handle(UpdateReviewCommand command) {
        var review = reviewRepository.findById(command.reviewId())
                .orElseThrow(() -> new NotFoundIdException(Review.class, command.reviewId()));

        if (!review.isActive()) {
            throw new IllegalStateException("Cannot update deleted review with id: " + command.reviewId());
        }

        review.update(new Rating(command.rating()), command.comment());

        try {
            var updated = reviewRepository.save(review);
            return Optional.of(updated);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error updating review: " + e.getMessage());
        }
    }

    @Override
    @Transactional
    public void handle(DeleteReviewCommand command) {
        var review = reviewRepository.findById(command.reviewId())
                .orElseThrow(() -> new NotFoundIdException(Review.class, command.reviewId()));

        review.delete();

        try {
            reviewRepository.save(review);
        } catch (Exception e) {
            throw new IllegalArgumentException("Error deleting review: " + e.getMessage());
        }
    }
}