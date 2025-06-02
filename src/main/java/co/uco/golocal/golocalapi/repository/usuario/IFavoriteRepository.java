package co.uco.golocal.golocalapi.repository.usuario;

import co.uco.golocal.golocalapi.data.entity.user.FavoriteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFavoriteRepository extends JpaRepository<FavoriteEntity, UUID>{
    boolean existsById(UUID id);
    boolean existsByExperienceId(UUID experienceId);
    boolean existsByUserId(UUID userId);
    FavoriteEntity findByUserId(UUID userId);
}
