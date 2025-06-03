package co.uco.golocal.golocalapi.repository.route;

import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface IRouteRepository extends JpaRepository<RouteEntity, UUID> {
    List<RouteEntity> findByCategoryId(UUID categoryId);
}