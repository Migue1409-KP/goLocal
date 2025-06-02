package co.uco.golocal.golocalapi.service.route;

import co.uco.golocal.golocalapi.data.mapper.concrete.IRouteMapperEntity;
import co.uco.golocal.golocalapi.repository.route.IRouteRepository;

public class RouteService {

    private final IRouteRepository routeRepository;
    private final IRouteMapperEntity routeMapperEntity;

    public RouteService(IRouteRepository routeRepository, IRouteMapperEntity routeMapperEntity) {
        this.routeRepository = routeRepository;
        this.routeMapperEntity = routeMapperEntity;
    }
}
