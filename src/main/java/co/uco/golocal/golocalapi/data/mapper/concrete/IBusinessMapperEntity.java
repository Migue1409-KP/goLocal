package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;

@Mapper(componentModel = "spring")
public interface IBusinessMapperEntity {
    IBusinessMapperEntity INSTANCE = Mappers.getMapper(IBusinessMapperEntity.class);

    @Mapping(target = "location.id", source = "locationId")
    @Mapping(target = "user.id", source = "userId")
    BusinessEntity toEntity(BusinessDomain businessDomain);
    @Mapping(source = "location.id", target = "locationId")
    @Mapping(source = "user.id", target = "userId")
    BusinessDomain toDomain(BusinessEntity businessEntity);

}
