package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.location.city.dto.CityDTO;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICityMapperDTO {
    CityDomain toDomain(CityDTO cityDTO);
    CityDTO toDTO(CityDomain cityDomain);
}