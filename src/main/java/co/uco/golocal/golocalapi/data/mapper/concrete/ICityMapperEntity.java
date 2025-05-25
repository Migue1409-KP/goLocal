package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

public interface ICityMapperEntity {

    ICityMapperEntity INSTANCE= Mappers.getMapper(ICityMapperEntity.class);

    CityEntity toEntity(CityDomain cityDomain);
    CityDomain toDomain(CityEntity entity);

    default Page<CityDomain> CitiesEntityPageToCitiesPage(Page<CityEntity> citiesEntityPage) {
        List<CityDomain> cityDomainList = citiesEntityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(cityDomainList, citiesEntityPage.getPageable(), citiesEntityPage.getTotalElements());
    }
}
