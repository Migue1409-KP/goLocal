package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import co.uco.golocal.golocalapi.domain.location.StateDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {ICityMapperEntity.class})
public interface IBusinessMapperEntity {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "location", target = "location")
    BusinessEntity toEntity(BusinessDomain domain);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "location", target = "location")
    BusinessDomain toDomain(BusinessEntity entity);
}
