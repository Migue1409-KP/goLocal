package co.uco.golocal.golocalapi.service.location.city;

import co.uco.golocal.golocalapi.data.entity.location.CityEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.ICityMapperEntity;
import co.uco.golocal.golocalapi.domain.location.CityDomain;
import co.uco.golocal.golocalapi.repository.location.ICityRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CityService {

    private final ICityRepository cityRepository;
    private final ICityMapperEntity cityMapperEntity;

    public CityService(ICityRepository cityRepository, ICityMapperEntity iCityMapperEntity) {
        this.cityRepository = cityRepository;
        this.cityMapperEntity = iCityMapperEntity;
    }

    public List<CityDomain> getAllCities() {
        List<CityEntity> entities = cityRepository.findAll();
        return entities.stream()
                .map(cityMapperEntity::toDomain)
                .collect(Collectors.toList());
    }

    public Optional<CityDomain> getCityById(UUID id) {
        return cityRepository.findById(id)
                .map(cityMapperEntity::toDomain);
    }
}