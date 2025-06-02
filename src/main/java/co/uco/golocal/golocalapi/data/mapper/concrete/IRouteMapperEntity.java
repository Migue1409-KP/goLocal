package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public interface IRouteMapperEntity {

    IRouteMapperEntity INSTANCE = Mappers.getMapper(IRouteMapperEntity.class);

    RouteEntity toEntity(RouteDomain routeDomain);
    RouteDomain toDomain(RouteEntity entity);

    default Page<RouteDomain> RoutesEntityPageToRoutesPage(Page<RouteEntity> routesEntityPage) {
        List<RouteDomain> routeDomainList = routesEntityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(routeDomainList, routesEntityPage.getPageable(), routesEntityPage.getTotalElements());
    }
}
