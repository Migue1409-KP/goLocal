package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.location.city.dto.CityDTO;
import co.uco.golocal.golocalapi.data.mapper.concrete.IStateMapperEntity;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring", uses = {IStateMapperEntity.class})
public interface ICityMapperDTO {

    @Mapping(source = "stateId", target = "state.id")
    CityDomain toDomain(CityDTO cityDTO);

    @Mapping(source = "state.id", target = "stateId")
    CityDTO toDTO(CityDomain cityDomain);
}