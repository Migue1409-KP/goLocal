package co.uco.golocal.golocalapi.repository.location;

import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICityRepository extends JpaRepository<CityEntity, UUID> {
}