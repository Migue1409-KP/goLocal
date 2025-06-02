package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.business.support.dto.BusinessRequestDTO;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface IBusinessMapperDTO {
    @Mapping(source = "locationId", target = "location.id")
    @Mapping(source = "userId", target = "userId")
    BusinessDomain toDomain(BusinessRequestDTO dto);
}
