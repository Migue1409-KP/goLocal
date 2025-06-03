package co.uco.golocal.golocalapi.repository.review;

import co.uco.golocal.golocalapi.data.entity.review.ReviewEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IReviewRepository extends JpaRepository<ReviewEntity, UUID> {

    List<ReviewEntity> findByUserId(UUID userId);

    List<ReviewEntity> findByExperienceId(UUID experienceId);

    List<ReviewEntity> findByRouteId(UUID routeId);

    List<ReviewEntity> findByRatingGreaterThanEqual(Double rating);

    boolean existsByUserIdAndRouteId(UUID userId, UUID routeId);

    boolean existsByUserIdAndExperienceId(UUID userId, UUID experienceId);

    List<ReviewEntity> findByRatingBetween(Double minRating, Double maxRating);

    @Query("SELECT COUNT(r) > 0 FROM ReviewEntity r WHERE (r.route IS NOT NULL AND r.experience IS NOT NULL) OR (r.route IS NULL AND r.experience IS NULL)")
    boolean existsWithBothRouteAndExperienceOrNone();
}