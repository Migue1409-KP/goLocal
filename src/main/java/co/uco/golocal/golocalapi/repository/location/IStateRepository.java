package co.uco.golocal.golocalapi.repository.location;

import co.uco.golocal.golocalapi.data.entity.location.StateEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IStateRepository extends JpaRepository<StateEntity, UUID> {
    @Override
    boolean existsById(UUID id);

    @Override
    Optional<StateEntity> findById(UUID id);
}