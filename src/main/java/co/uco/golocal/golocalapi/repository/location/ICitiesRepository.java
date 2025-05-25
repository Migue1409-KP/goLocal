package co.uco.golocal.golocalapi.repository.location;

import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ICitiesRepository extends JpaRepository<CityEntity, UUID> {
    boolean existsById(UUID id);
    Optional<CityEntity> findById(UUID id);
}
