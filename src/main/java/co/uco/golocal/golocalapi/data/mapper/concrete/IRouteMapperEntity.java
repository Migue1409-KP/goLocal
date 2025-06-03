package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {ICategoryMapperEntity.class, IExperienceMapperEntity.class})
public interface IRouteMapperEntity {

    RouteEntity toEntity(RouteDomain routeDomain);

    RouteDomain toDomain(RouteEntity entity);

    List<RouteDomain> toDomainList(List<RouteEntity> entityList);

    default Page<RouteDomain> toDomainPage(Page<RouteEntity> entityPage) {
        List<RouteDomain> domains = entityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(domains, entityPage.getPageable(), entityPage.getTotalElements());
    }
}