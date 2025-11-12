package pe.edu.upc.center.workstation.reviewsManagement.infrastructure.persistence.jpa.repositories;

import pe.edu.upc.center.workstation.reviewsManagement.domain.model.aggregates.Review;
import pe.edu.upc.center.workstation.reviewsManagement.domain.model.valueobjects.ReviewStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    @Query("SELECT r FROM Review r WHERE r.userId.userId = :userId AND r.spaceId.spaceId = :spaceId AND r.status = 'PUBLISHED'")
    Optional<Review> findActiveByUserAndSpace(
            @Param("userId") Long userId,
            @Param("spaceId") Long spaceId
    );

    @Query("SELECT r FROM Review r WHERE r.spaceId.spaceId = :spaceId AND r.status = 'PUBLISHED'")
    Page<Review> findBySpaceId(@Param("spaceId") Long spaceId, Pageable pageable);

    @Query("SELECT r FROM Review r WHERE r.userId.userId = :userId AND r.status = 'PUBLISHED'")
    List<Review> findByUserId(@Param("userId") Long userId);

    @Query("SELECT AVG(r.rating.value) FROM Review r WHERE r.spaceId.spaceId = :spaceId AND r.status = 'PUBLISHED'")
    Double calculateAverageRatingBySpace(@Param("spaceId") Long spaceId);

    @Query("SELECT COUNT(r) FROM Review r WHERE r.spaceId.spaceId = :spaceId AND r.status = 'PUBLISHED'")
    Long countPublishedBySpace(@Param("spaceId") Long spaceId);

    List<Review> findByStatus(ReviewStatus status);
}