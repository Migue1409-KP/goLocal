package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IBusinessMapperEntity {

    default BusinessEntity toEntity(BusinessDomain domain) {
        if (domain == null) return null;

        CityEntity city = new CityEntity();
        city.setId(domain.getLocation().getId());

        UserEntity user = new UserEntity();
        user.setId(domain.getUserId());

        return BusinessEntity.builder()
                .id(domain.getId())
                .name(domain.getName())
                .description(domain.getDescription())
                .location(city)
                .user(user)
                .createdAt(domain.getCreatedAt())
                .updatedAt(domain.getUpdatedAt())
                .build();
    }

    default BusinessDomain toDomain(BusinessEntity entity) {
        if (entity == null) return null;

        CityDomain city = new CityDomain();
        city.setId(entity.getLocation().getId());
        city.setName(entity.getLocation().getName()); // opcional

        return BusinessDomain.builder()
                .id(entity.getId())
                .name(entity.getName())
                .description(entity.getDescription())
                .location(city)
                .userId(entity.getUser().getId())
                .createdAt(entity.getCreatedAt())
                .updatedAt(entity.getUpdatedAt())
                .build();
    }
}
