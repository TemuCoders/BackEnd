package pe.edu.upc.center.workstation.reviews.interfaces.rest.query;

import pe.edu.upc.center.workstation.reviews.domain.model.queries.*;
import pe.edu.upc.center.workstation.reviews.domain.services.ReviewQueryService;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.resources.*;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.transform.ReviewResourceFromEntityAssembler;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", methods = {RequestMethod.GET})
@RestController
@RequestMapping(value = "/api/v1", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reviews - Queries", description = "Review read operations")
public class ReviewQueryController {

    private final ReviewQueryService reviewQueryService;

    public ReviewQueryController(ReviewQueryService reviewQueryService) {
        this.reviewQueryService = reviewQueryService;
    }

    @GetMapping("/reviews/{reviewId}")
    @Operation(summary = "Get review by ID", description = "Retrieves a single review by its identifier")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review found"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<ReviewResource> getReviewById(@PathVariable Long reviewId) {
        var query = new GetReviewByIdQuery(reviewId);
        var optionalReview = reviewQueryService.handle(query);

        if (optionalReview.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        var resource = ReviewResourceFromEntityAssembler.toResourceFromEntity(optionalReview.get());
        return ResponseEntity.ok(resource);
    }

    @GetMapping("/spaces/{spaceId}/reviews")
    @Operation(summary = "Get all reviews for a space (paginated)", description = "Retrieves paginated reviews for a specific space")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    })
    public ResponseEntity<Page<ReviewResource>> getReviewsBySpace(
            @PathVariable Long spaceId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        var query = new GetReviewsBySpaceQuery(spaceId, page, size);
        var reviews = reviewQueryService.handle(query);
        var resources = reviews.map(ReviewResourceFromEntityAssembler::toResourceFromEntity);
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/users/{userId}/reviews")
    @Operation(summary = "Get all reviews by a user", description = "Retrieves all reviews created by a specific user")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Reviews retrieved successfully")
    })
    public ResponseEntity<List<ReviewResource>> getReviewsByUser(@PathVariable Long userId) {
        var query = new GetReviewsByUserQuery(userId);
        var reviews = reviewQueryService.handle(query);
        var resources = reviews.stream()
                .map(ReviewResourceFromEntityAssembler::toResourceFromEntity)
                .collect(Collectors.toList());
        return ResponseEntity.ok(resources);
    }

    @GetMapping("/spaces/{spaceId}/reviews/summary")
    @Operation(summary = "Get review summary for a space", description = "Retrieves average rating and total count of reviews")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Summary retrieved successfully")
    })
    public ResponseEntity<ReviewSummaryResource> getReviewsSummaryBySpace(@PathVariable Long spaceId) {
        var query = new GetReviewsSummaryBySpaceQuery(spaceId);
        var summary = reviewQueryService.handle(query);
        var resource = new ReviewSummaryResource(summary.averageRating(), summary.totalCount());
        return ResponseEntity.ok(resource);
    }
}