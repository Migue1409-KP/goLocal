package co.uco.golocal.golocalapi.controllers.review;

import co.uco.golocal.golocalapi.controllers.review.dto.ReviewDTO;
import co.uco.golocal.golocalapi.controllers.mapper.IReviewMapperDTO;
import co.uco.golocal.golocalapi.domain.review.ReviewDomain;
import co.uco.golocal.golocalapi.service.review.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/rest/reviews")
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final IReviewMapperDTO reviewMapperDTO;

    @PostMapping
    public ResponseEntity<ReviewDTO> create(@Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDomain domain = reviewMapperDTO.toDomain(reviewDTO);
        ReviewDomain createdDomain = reviewService.create(domain);
        return ResponseEntity.status(HttpStatus.CREATED).body(reviewMapperDTO.toDTO(createdDomain));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ReviewDTO> findById(@PathVariable UUID id) {
        ReviewDomain domain = reviewService.findById(id);
        return ResponseEntity.ok(reviewMapperDTO.toDTO(domain));
    }

    @GetMapping
    public ResponseEntity<Page<ReviewDTO>> findAll(Pageable pageable) {
        Page<ReviewDomain> domains = reviewService.findAll(pageable);
        return ResponseEntity.ok(reviewMapperDTO.toDTOPage(domains));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ReviewDTO> update(@PathVariable UUID id, @Valid @RequestBody ReviewDTO reviewDTO) {
        ReviewDomain domain = reviewMapperDTO.toDomain(reviewDTO);
        ReviewDomain updatedDomain = reviewService.update(id, domain);
        return ResponseEntity.ok(reviewMapperDTO.toDTO(updatedDomain));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id) {
        reviewService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/by-user/{userId}")
    public ResponseEntity<List<ReviewDTO>> findByUserId(@PathVariable UUID userId) {
        List<ReviewDomain> domains = reviewService.findByUserId(userId);
        return ResponseEntity.ok(reviewMapperDTO.toDTOList(domains));
    }

    @GetMapping("/by-experience/{experienceId}")
    public ResponseEntity<List<ReviewDTO>> findByExperienceId(@PathVariable UUID experienceId) {
        List<ReviewDomain> domains = reviewService.findByExperienceId(experienceId);
        return ResponseEntity.ok(reviewMapperDTO.toDTOList(domains));
    }

    @GetMapping("/by-route/{routeId}")
    public ResponseEntity<List<ReviewDTO>> findByRouteId(@PathVariable UUID routeId) {
        List<ReviewDomain> domains = reviewService.findByRouteId(routeId);
        return ResponseEntity.ok(reviewMapperDTO.toDTOList(domains));
    }

    @GetMapping("/by-rating/{rating}")
    public ResponseEntity<List<ReviewDTO>> findByRatingGreaterThanEqual(@PathVariable Double rating) {
        List<ReviewDomain> domains = reviewService.findByRatingGreaterThanEqual(rating);
        return ResponseEntity.ok(reviewMapperDTO.toDTOList(domains));
    }

    @GetMapping("/by-rating-range")
    public ResponseEntity<List<ReviewDTO>> findByRatingBetween(
            @RequestParam Double minRating,
            @RequestParam Double maxRating) {
        List<ReviewDomain> domains = reviewService.findByRatingBetween(minRating, maxRating);
        return ResponseEntity.ok(reviewMapperDTO.toDTOList(domains));
    }

    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<String> handleIllegalArgumentException(IllegalArgumentException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ex.getMessage());
    }

    @ExceptionHandler(RuntimeException.class)
    public ResponseEntity<String> handleRuntimeException(RuntimeException ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(ex.getMessage());
    }
}