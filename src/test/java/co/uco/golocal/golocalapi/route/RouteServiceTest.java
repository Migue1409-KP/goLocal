package co.uco.golocal.golocalapi.route;

import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IRouteMapperEntity;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.domain.route.rules.Validate;
import co.uco.golocal.golocalapi.repository.route.IRouteRepository;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import co.uco.golocal.golocalapi.service.route.RouteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.*;

class RouteServiceTest {
@Mock
private IRouteRepository routeRepository;

@Mock
private IRouteMapperEntity routeMapperEntity;

@Mock
private IUserRepository userRepository;

@Mock
private Validate<RouteDomain> validator;

@InjectMocks
private RouteService routeService;

private final UUID routeId = UUID.randomUUID();
private final UUID userId = UUID.randomUUID();

@BeforeEach
void setUp() {
    MockitoAnnotations.openMocks(this);
    routeService = new RouteService(routeRepository, routeMapperEntity, List.of(validator), userRepository);
}

@Test
void testGetAllRoutes() {
    RouteEntity entity = new RouteEntity();
    RouteDomain domain = new RouteDomain();
    when(routeRepository.findAll()).thenReturn(List.of(entity));
    when(routeMapperEntity.toDomainList(anyList())).thenReturn(List.of(domain));

    List<RouteDomain> result = routeService.getAllRoutes();

    assertEquals(1, result.size());
    verify(routeRepository).findAll();
    verify(routeMapperEntity).toDomainList(anyList());
}

@Test
void testCreateRoute() {
    RouteDomain domain = new RouteDomain();
    RouteEntity entity = new RouteEntity();
    RouteEntity savedEntity = new RouteEntity();

    when(userRepository.findById(userId)).thenReturn(Optional.of(new UserEntity()));
    when(routeMapperEntity.toEntity(domain)).thenReturn(entity);
    when(routeRepository.save(entity)).thenReturn(savedEntity);
    when(routeMapperEntity.toDomain(savedEntity)).thenReturn(domain);

    RouteDomain result = routeService.createRoute(domain, userId);

    assertNotNull(result);
    verify(routeRepository).save(entity);
}

@Test
void testUpdateRoute() {
    RouteDomain domain = new RouteDomain();
    RouteEntity existingEntity = new RouteEntity();
    existingEntity.setUser(new UserEntity());
    RouteEntity updatedEntity = new RouteEntity();

    when(routeRepository.findById(routeId)).thenReturn(Optional.of(existingEntity));
    when(routeMapperEntity.toEntity(domain)).thenReturn(updatedEntity);
    when(routeRepository.save(updatedEntity)).thenReturn(updatedEntity);
    when(routeMapperEntity.toDomain(updatedEntity)).thenReturn(domain);

    RouteDomain result = routeService.updateRoute(routeId, domain);

    assertNotNull(result);
    verify(validator).execute(domain);
    verify(routeRepository).save(updatedEntity);
}

@Test
void testDeleteRoute() {
    when(routeRepository.existsById(routeId)).thenReturn(true);
    doNothing().when(routeRepository).deleteById(routeId);

    routeService.deleteRoute(routeId);

    verify(routeRepository).deleteById(routeId);
}

@Test
void testDeleteRouteNotFound() {
    when(routeRepository.existsById(routeId)).thenReturn(false);
    assertThrows(IllegalArgumentException.class, () -> routeService.deleteRoute(routeId));
}
}
