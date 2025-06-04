package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.business.dto.BusinessDTO;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IBusinessMapperDTO {
    @Mapping(source = "locationId", target = "location.id")
    @Mapping(source = "userId", target = "userId")
    @Mapping(source = "categories", target = "categories", qualifiedByName = "mapCategoryIdsToDomains")
    BusinessDomain toDomain(BusinessDTO dto);

    @Named("mapCategoryIdsToDomains")
    default List<CategoryDomain> mapCategoryIdsToDomains(List<UUID> categoryIds) {
        if (categoryIds == null) return null;
        return categoryIds.stream()
                .map(id -> CategoryDomain.builder().id(id).build())
                .collect(Collectors.toList());
    }
}
