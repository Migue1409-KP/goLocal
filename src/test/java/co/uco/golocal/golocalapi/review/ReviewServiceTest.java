package co.uco.golocal.golocalapi.review;

import co.uco.golocal.golocalapi.data.entity.review.ReviewEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IReviewMapperEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.review.ReviewDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.repository.review.IReviewRepository;
import co.uco.golocal.golocalapi.service.review.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private IReviewRepository reviewRepository;

    @Mock
    private IReviewMapperEntity reviewMapper;

    @InjectMocks
    private ReviewService reviewService;

    private UUID reviewId;
    private UUID userId;
    private UUID experienceId;
    private UserDomain user;
    private ExperienceDomain experience;

    @BeforeEach
    void setUp() {
        reviewId = UUID.randomUUID();
        userId = UUID.randomUUID();
        experienceId = UUID.randomUUID();
        user = new UserDomain();
        user.setId(userId);
        experience = new ExperienceDomain();
        experience.setId(experienceId);
    }

    @Test
    void createNewReview_shouldReturnSavedReview() {
        // Arrange
        ReviewDomain reviewDomain = new ReviewDomain(
                null, 4.5, user, new Date(), "Muy buena experiencia",
                experience, null, LocalDateTime.now(), LocalDateTime.now()
        );

        ReviewEntity reviewEntity = new ReviewEntity();
        ReviewEntity savedEntity = new ReviewEntity();
        ReviewDomain mappedDomain = new ReviewDomain(
                reviewId, 4.5, user, new Date(), "Muy buena experiencia",
                experience, null, LocalDateTime.now(), LocalDateTime.now()
        );

        when(reviewRepository.findByUserIdAndExperienceId(userId, experienceId)).thenReturn(null);
        when(reviewMapper.toEntity(reviewDomain)).thenReturn(reviewEntity);
        when(reviewRepository.save(reviewEntity)).thenReturn(savedEntity);
        when(reviewMapper.toDomain(savedEntity)).thenReturn(mappedDomain);

        // Act
        ReviewDomain result = reviewService.create(reviewDomain);

        // Assert
        assertNotNull(result);
        assertEquals(4.5, result.getRating());
        assertEquals("Muy buena experiencia", result.getDescription());
    }

    @Test
    void findById_shouldReturnReview() {
        // Arrange
        ReviewEntity entity = new ReviewEntity();
        ReviewDomain expected = new ReviewDomain(
                reviewId, 5.0, user, new Date(), "Excelente",
                experience, null, LocalDateTime.now(), LocalDateTime.now()
        );

        when(reviewRepository.findById(reviewId)).thenReturn(Optional.of(entity));
        when(reviewMapper.toDomain(entity)).thenReturn(expected);

        // Act
        ReviewDomain result = reviewService.findById(reviewId);

        // Assert
        assertNotNull(result);
        assertEquals(expected, result);
    }

    @Test
    void delete_existingReview_shouldSucceed() {
        // Arrange
        when(reviewRepository.existsById(reviewId)).thenReturn(true);
        doNothing().when(reviewRepository).deleteById(reviewId);

        // Act & Assert
        assertDoesNotThrow(() -> reviewService.delete(reviewId));
        verify(reviewRepository).deleteById(reviewId);
    }
}
