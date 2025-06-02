package co.uco.golocal.golocalapi.repository.usuario;

import co.uco.golocal.golocalapi.data.entity.user.FavorityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IFavorityRepository extends JpaRepository<FavorityEntity, UUID>{
    boolean existsById(UUID id);
    boolean existsByExperienceId(UUID experienceId);
    boolean existsByUserId(UUID userId);
    FavorityEntity findByUserId(UUID userId);
}
