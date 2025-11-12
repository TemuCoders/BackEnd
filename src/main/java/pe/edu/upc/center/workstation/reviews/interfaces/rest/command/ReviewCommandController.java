package pe.edu.upc.center.workstation.reviews.interfaces.rest.command;

import pe.edu.upc.center.workstation.reviews.domain.model.commands.DeleteReviewCommand;
import pe.edu.upc.center.workstation.reviews.domain.model.queries.GetReviewByIdQuery;
import pe.edu.upc.center.workstation.reviews.domain.services.ReviewCommandService;
import pe.edu.upc.center.workstation.reviews.domain.services.ReviewQueryService;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.resources.*;
import pe.edu.upc.center.workstation.reviews.interfaces.rest.transform.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "*", methods = {RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE})
@RestController
@RequestMapping(value = "/api/v1/reviews", produces = MediaType.APPLICATION_JSON_VALUE)
@Tag(name = "Reviews - Commands", description = "Review write operations")
public class ReviewCommandController {

    private final ReviewCommandService reviewCommandService;
    private final ReviewQueryService reviewQueryService;

    public ReviewCommandController(
            ReviewCommandService reviewCommandService,
            ReviewQueryService reviewQueryService
    ) {
        this.reviewCommandService = reviewCommandService;
        this.reviewQueryService = reviewQueryService;
    }

    @PostMapping
    @Operation(summary = "Create a new review", description = "Creates a review for a space by a user")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Review created successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input or duplicate review"),
            @ApiResponse(responseCode = "404", description = "User or Space not found")
    })
    public ResponseEntity<ReviewResource> createReview(@Valid @RequestBody CreateReviewResource resource) {
        var command = CreateReviewCommandFromResourceAssembler.toCommandFromResource(resource);
        var reviewId = reviewCommandService.handle(command);

        if (reviewId == null || reviewId <= 0) {
            return ResponseEntity.badRequest().build();
        }

        var query = new GetReviewByIdQuery(reviewId);
        var optionalReview = reviewQueryService.handle(query);

        if (optionalReview.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(optionalReview.get());
        return new ResponseEntity<>(reviewResource, HttpStatus.CREATED);
    }

    @PutMapping("/{reviewId}")
    @Operation(summary = "Update an existing review", description = "Updates rating and comment of a review")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Review updated successfully"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<ReviewResource> updateReview(
            @PathVariable Long reviewId,
            @Valid @RequestBody UpdateReviewResource resource
    ) {
        var command = UpdateReviewCommandFromResourceAssembler.toCommandFromResource(reviewId, resource);
        var optionalReview = reviewCommandService.handle(command);

        if (optionalReview.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        var reviewResource = ReviewResourceFromEntityAssembler.toResourceFromEntity(optionalReview.get());
        return ResponseEntity.ok(reviewResource);
    }

    @DeleteMapping("/{reviewId}")
    @Operation(summary = "Delete a review (soft delete)", description = "Marks a review as deleted")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Review deleted successfully"),
            @ApiResponse(responseCode = "404", description = "Review not found")
    })
    public ResponseEntity<?> deleteReview(@PathVariable Long reviewId) {
        var command = new DeleteReviewCommand(reviewId);
        reviewCommandService.handle(command);
        return ResponseEntity.noContent().build();
    }
}