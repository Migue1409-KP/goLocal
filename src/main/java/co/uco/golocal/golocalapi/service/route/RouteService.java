package co.uco.golocal.golocalapi.service.route;

import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IRouteMapperEntity;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.domain.route.rules.Validate;
import co.uco.golocal.golocalapi.repository.route.IRouteRepository;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RouteService {

    private final IRouteRepository routeRepository;
    private final IRouteMapperEntity routeMapperEntity;
    private final List<Validate<RouteDomain>> validators;
    private final IUserRepository userRepository;

    public RouteService(IRouteRepository routeRepository,
                        IRouteMapperEntity routeMapperEntity,
                        List<Validate<RouteDomain>> validators,
                        IUserRepository userRepository) {
        this.routeRepository = routeRepository;
        this.routeMapperEntity = routeMapperEntity;
        this.validators = validators;
        this.userRepository = userRepository;
    }

    public List<RouteDomain> getAllRoutes() {
        List<RouteEntity> entities = routeRepository.findAll();
        return routeMapperEntity.toDomainList(entities);
    }

    public Page<RouteDomain> getAllRoutes(Pageable pageable) {
        Page<RouteEntity> entityPage = routeRepository.findAll(pageable);
        return routeMapperEntity.toDomainPage(entityPage);
    }

    public Optional<RouteEntity> getRouteById(UUID id) {
        return routeRepository.findById(id);
    }

    public List<RouteDomain> getRoutesByCategory(UUID categoryId) {
        List<RouteEntity> entities = routeRepository.findByCategoryId(categoryId);
        return routeMapperEntity.toDomainList(entities);
    }

    // En tu servicio o controlador donde creas la ruta
    public RouteDomain createRoute(RouteDomain routeDomain, UUID userId) {
        // Obtener el usuario de la base de datos
        UserEntity userEntity = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("Usuario no encontrado"));

        // Asignar el usuario a la entidad de ruta antes de guardar
        RouteEntity routeEntity = routeMapperEntity.toEntity(routeDomain);
        routeEntity.setUser(userEntity);

        // Guardar la ruta
        RouteEntity savedRoute = routeRepository.save(routeEntity);
        return routeMapperEntity.toDomain(savedRoute);
    }

    public RouteDomain updateRoute(UUID id, RouteDomain routeDomain) {
        // Verificar si existe la ruta
        RouteEntity existingRoute = routeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Ruta no encontrada con ID: " + id));

        // Validar la ruta
        validators.forEach(validator -> validator.execute(routeDomain));

        // Obtener el usuario asociado a la ruta existente
        UserEntity userEntity = existingRoute.getUser();

        // Actualizar la entidad
        routeDomain.setId(id);
        RouteEntity entity = routeMapperEntity.toEntity(routeDomain);
        entity.setUser(userEntity); // Asignar el usuario existente

        // Guardar la entidad actualizada
        entity = routeRepository.save(entity);

        return routeMapperEntity.toDomain(entity);
    }

    public void deleteRoute(UUID id) {
        // Verificar si existe
        if (!routeRepository.existsById(id)) {
            throw new IllegalArgumentException("Ruta no encontrada con ID: " + id);
        }

        routeRepository.deleteById(id);
    }

    public IRouteMapperEntity getRouteMapper() {
        return routeMapperEntity;
    }

    public List<RouteDomain> getRoutesByUserId(UUID userId) {
        List<RouteEntity> entities = routeRepository.findByUser_Id(userId);
        return routeMapperEntity.toDomainList(entities);
    }

    public Page<RouteDomain> getRoutesByUserId(UUID userId, Pageable pageable) {
        Page<RouteEntity> entityPage = routeRepository.findByUser_Id(userId, pageable);
        return routeMapperEntity.toDomainPage(entityPage);
    }
}