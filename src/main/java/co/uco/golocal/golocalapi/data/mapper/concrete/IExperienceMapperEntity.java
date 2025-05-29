package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;

@Mapper(componentModel = "spring")
public interface IExperienceMapperEntity {
    @Mapping(source="businessId", target="business.id")
    @Mapping(source="categoryId", target="category.id")
    ExperienceEntity toEntity(ExperienceDomain experienceDomain);

    @Mapping(source="business.id", target="businessId")
    @Mapping(source="category.id", target="categoryId")
    ExperienceDomain toDomain(ExperienceEntity experienceEntity);

}
