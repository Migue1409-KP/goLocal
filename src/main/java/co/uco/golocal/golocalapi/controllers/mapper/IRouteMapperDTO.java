package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.route.dto.RouteDTO;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IRouteMapperDTO {

    @Mapping(target = "category", source = "categoryId", qualifiedByName = "uuidsToCategoryDomains")
    @Mapping(target = "experience", source = "experienceId", qualifiedByName = "uuidsToExperienceDomains")
    RouteDomain toDomain(RouteDTO dto);

    @Mapping(target = "categoryId", source = "category", qualifiedByName = "categoryDomainsToUuids")
    @Mapping(target = "experienceId", source = "experience", qualifiedByName = "experienceDomainsToUuids")
    RouteDTO toDto(RouteDomain domain);

    @Named("uuidsToCategoryDomains")
    default List<CategoryDomain> uuidsToCategoryDomains(List<UUID> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> CategoryDomain.builder().id(id).build())
                .collect(Collectors.toList());
    }

    @Named("uuidsToExperienceDomains")
    default List<ExperienceDomain> uuidsToExperienceDomains(List<UUID> ids) {
        if (ids == null) return null;
        return ids.stream()
                .map(id -> ExperienceDomain.builder().id(id).build())
                .collect(Collectors.toList());
    }

    @Named("categoryDomainsToUuids")
    default List<UUID> categoryDomainsToUuids(List<CategoryDomain> domains) {
        if (domains == null) return null;
        return domains.stream()
                .map(CategoryDomain::getId)
                .collect(Collectors.toList());
    }

    @Named("experienceDomainsToUuids")
    default List<UUID> experienceDomainsToUuids(List<ExperienceDomain> domains) {
        if (domains == null) return null;
        return domains.stream()
                .map(ExperienceDomain::getId)
                .collect(Collectors.toList());
    }
}