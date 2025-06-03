package co.uco.golocal.golocalapi.service.review;

import co.uco.golocal.golocalapi.data.entity.review.ReviewEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IReviewMapperEntity;
import co.uco.golocal.golocalapi.domain.review.ReviewDomain;
import co.uco.golocal.golocalapi.repository.review.IReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
@Transactional
public class ReviewService {

    private static final double MIN_RATING = 0.0;
    private static final double MAX_RATING = 5.0;
    private static final int MAX_DESCRIPTION_LENGTH = 250;
    private static final Pattern VALID_DESCRIPTION_PATTERN = Pattern.compile("^[a-zA-Z0-9áéíóúÁÉÍÓÚñÑ\\s.,;:¡!¿?-]+$");

    private final IReviewRepository reviewRepository;
    private final IReviewMapperEntity reviewMapper;

    public ReviewDomain create(ReviewDomain reviewDomain) {
        validateReview(reviewDomain);
        ReviewEntity entity = reviewMapper.toEntity(reviewDomain);
        ReviewEntity savedEntity = reviewRepository.save(entity);
        return reviewMapper.toDomain(savedEntity);
    }

    @Transactional(readOnly = true)
    public ReviewDomain findById(UUID id) {
        return reviewRepository.findById(id)
                .map(reviewMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Review no encontrada con id: " + id));
    }

    @Transactional(readOnly = true)
    public Page<ReviewDomain> findAll(Pageable pageable) {
        Page<ReviewEntity> entityPage = reviewRepository.findAll(pageable);
        return reviewMapper.toDomainPage(entityPage);
    }

    public ReviewDomain update(UUID id, ReviewDomain reviewDomain) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review no encontrada con id: " + id);
        }

        reviewDomain.setId(id);
        validateReview(reviewDomain);
        ReviewEntity entity = reviewMapper.toEntity(reviewDomain);
        ReviewEntity updatedEntity = reviewRepository.save(entity);

        return reviewMapper.toDomain(updatedEntity);
    }

    public void delete(UUID id) {
        if (!reviewRepository.existsById(id)) {
            throw new RuntimeException("Review no encontrada con id: " + id);
        }
        reviewRepository.deleteById(id);
    }

    @Transactional(readOnly = true)
    public List<ReviewDomain> findByUserId(UUID userId) {
        List<ReviewEntity> entities = reviewRepository.findByUserId(userId);
        return reviewMapper.toDomainList(entities);
    }

    @Transactional(readOnly = true)
    public List<ReviewDomain> findByExperienceId(UUID experienceId) {
        List<ReviewEntity> entities = reviewRepository.findByExperienceId(experienceId);
        return reviewMapper.toDomainList(entities);
    }

    @Transactional(readOnly = true)
    public List<ReviewDomain> findByRouteId(UUID routeId) {
        List<ReviewEntity> entities = reviewRepository.findByRouteId(routeId);
        return reviewMapper.toDomainList(entities);
    }

    @Transactional(readOnly = true)
    public List<ReviewDomain> findByRatingGreaterThanEqual(Double rating) {
        List<ReviewEntity> entities = reviewRepository.findByRatingGreaterThanEqual(rating);
        return reviewMapper.toDomainList(entities);
    }

    private void validateReview(ReviewDomain reviewDomain) {
        // Validar calificación entre 0 y 5
        if (reviewDomain.getRating() < MIN_RATING || reviewDomain.getRating() > MAX_RATING) {
            throw new IllegalArgumentException("La calificación debe estar entre 0 y 5");
        }

        // Validar descripción
        if (reviewDomain.getDescription() != null) {
            // Validar longitud máxima
            if (reviewDomain.getDescription().length() > MAX_DESCRIPTION_LENGTH) {
                throw new IllegalArgumentException("La descripción no puede exceder los 250 caracteres");
            }

            // Validar caracteres permitidos
            if (!VALID_DESCRIPTION_PATTERN.matcher(reviewDomain.getDescription()).matches()) {
                throw new IllegalArgumentException("La descripción contiene caracteres no permitidos");
            }
        }

        boolean hasRouteId = reviewDomain.getRouteId() != null;
        boolean hasExperienceId = reviewDomain.getExperienceId() != null;

        if ((hasRouteId && hasExperienceId) || (!hasRouteId && !hasExperienceId)) {
            throw new IllegalArgumentException("La reseña debe estar asociada a una ruta o a una experiencia, no a ambas o ninguna");
        }
    }
    @Transactional(readOnly = true)
    public List<ReviewDomain> findByRatingBetween(Double minRating, Double maxRating) {
        // Validar valores de entrada
        if (minRating < MIN_RATING || maxRating > MAX_RATING || minRating > maxRating) {
            throw new IllegalArgumentException("Rango de calificación inválido");
        }

        List<ReviewEntity> entities = reviewRepository.findByRatingBetween(minRating, maxRating);
        return reviewMapper.toDomainList(entities);
    }
}