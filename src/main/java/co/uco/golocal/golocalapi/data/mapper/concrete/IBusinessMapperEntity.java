package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {ICityMapperEntity.class, ICategoryMapperEntity.class, IExperienceMapperEntity.class})
public interface IBusinessMapperEntity {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "location", target = "location")
    BusinessEntity toEntity(BusinessDomain domain);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "location", target = "location")
    @Mapping(source = "categories", target = "categories")
    @Mapping(source = "experiences", target = "experiences")
    BusinessDomain toDomain(BusinessEntity entity);

//    @Named("mapCategoriesToIds")
//    default List<UUID> mapCategoriesToIds(List<CategoryEntity> categories) {
//        if (categories == null) return null;
//        return categories.stream().map(CategoryEntity::getId).collect(Collectors.toList());
//    }

}
