package co.uco.golocal.golocalapi.controllers.route;

import co.uco.golocal.golocalapi.controllers.mapper.IRouteMapperDTO;
import co.uco.golocal.golocalapi.controllers.route.dto.RouteDTO;
import co.uco.golocal.golocalapi.controllers.support.Response;
import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.service.route.RouteService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@Validated
@RequestMapping("/api/v1/rest/routes")
public class RouteController {

    private final RouteService routeService;
    private final IRouteMapperDTO routeMapper;

    public RouteController(RouteService routeService, IRouteMapperDTO routeMapper) {
        this.routeService = routeService;
        this.routeMapper = routeMapper;
    }

    @GetMapping("/dummy")
    public RouteDomain getDummy() {
        return new RouteDomain();
    }

    @PostMapping
    public ResponseEntity<Response<RouteDomain>> createRoute(@Valid @RequestBody RouteDTO routeDTO) {
        Response<RouteDomain> response = new Response<>();
        try {
            // Convertir DTO a dominio
            RouteDomain domain = routeMapper.toDomain(routeDTO);

            // Extraer el userId del DTO
            UUID userId = routeDTO.getUserId();

            // Llamar al servicio con el dominio y el userId
            RouteDomain createdRoute = routeService.createRoute(domain, userId);

            // Construir respuesta
            response.setStatus(HttpStatus.CREATED);
            response.setMessage("Ruta registrada exitosamente");
            response.setData(List.of(createdRoute));

            return ResponseEntity.status(HttpStatus.CREATED).body(response);
        } catch (Exception e) {
            response.setMessage("Error al registrar la ruta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Response<RouteDomain>> getRouteById(@PathVariable UUID id) {
        Response<RouteDomain> response = new Response<>();
        try {
            Optional<RouteEntity> optional = routeService.getRouteById(id);
            if (optional.isPresent()) {
                RouteDomain domain = routeService.getRouteMapper().toDomain(optional.get());

                response.setStatus(HttpStatus.OK);
                response.setMessage("Ruta encontrada correctamente");
                response.setData(List.of(domain));

                return new ResponseEntity<>(response, HttpStatus.OK);
            } else {
                response.setStatus(HttpStatus.NOT_FOUND);
                response.setMessage("No se encontró la ruta con ID: " + id);
                return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al recuperar la ruta: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/category/{categoryId}")
    public ResponseEntity<Response<RouteDomain>> getRoutesByCategory(@PathVariable UUID categoryId) {
        Response<RouteDomain> response = new Response<>();
        try {
            List<RouteDomain> routes = routeService.getRoutesByCategory(categoryId);

            response.setStatus(HttpStatus.OK);
            response.setMessage("Rutas encontradas correctamente");
            response.setData(routes);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al recuperar las rutas por categoría: " + e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Response<RouteDomain>> updateRoute(
            @PathVariable UUID id,
            @RequestBody RouteDomain routeDomain) {

        Response<RouteDomain> response = new Response<>();

        try {
            RouteDomain updated = routeService.updateRoute(id, routeDomain);

            response.setStatus(HttpStatus.OK);
            response.setData(List.of(updated));
            response.setMessage("Ruta actualizada correctamente");

            return ResponseEntity.ok(response);

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al actualizar la ruta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }


    }

    @GetMapping
    public ResponseEntity<Response<RouteDomain>> getAllRoutes(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<RouteDomain> routePage = routeService.getAllRoutes(pageable);

        Response<RouteDomain> response = new Response<>();
        response.setStatus(HttpStatus.OK);
        response.setData(routePage.getContent());
        response.setMessage("Rutas listadas exitosamente");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Response<Void>> deleteRoute(@PathVariable UUID id) {
        Response<Void> response = new Response<>();

        try {
            routeService.deleteRoute(id);
            response.setStatus(HttpStatus.OK);
            response.setMessage("Ruta eliminada correctamente");
            return ResponseEntity.status(HttpStatus.OK).body(response);

        } catch (IllegalArgumentException e) {
            response.setStatus(HttpStatus.NOT_FOUND);
            response.setMessage(e.getMessage());
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
        } catch (Exception e) {
            response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
            response.setMessage("Error al eliminar la ruta: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
        }
    }

    @GetMapping("/user/{userId}")
    public ResponseEntity<List<RouteDomain>> getRoutesByUserId(@PathVariable UUID userId) {
        List<RouteDomain> routes = routeService.getRoutesByUserId(userId);
        return ResponseEntity.ok(routes);
    }

    @GetMapping("/user/{userId}/paged")
    public ResponseEntity<Page<RouteDomain>> getPagedRoutesByUserId(
            @PathVariable UUID userId,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String direction) {

        Sort.Direction sortDirection = direction.equalsIgnoreCase("asc") ?
                Sort.Direction.ASC : Sort.Direction.DESC;

        Pageable pageable = PageRequest.of(page, size, Sort.by(sortDirection, sortBy));
        Page<RouteDomain> routePage = routeService.getRoutesByUserId(userId, pageable);

        return ResponseEntity.ok(routePage);
    }
}