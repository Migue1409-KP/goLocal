package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.location.StateEntity;
import co.uco.golocal.golocalapi.domain.location.StateDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IStateMapperEntity {

    IStateMapperEntity INSTANCE= Mappers.getMapper(IStateMapperEntity.class);

    StateEntity toEntity(StateDomain StateDomain);
    StateDomain toDomain(StateEntity entity);

    default Page<StateDomain> StateEntityPageToStatePage(Page<StateEntity> StateEntityPage) {
        List<StateDomain> StateDomainList = StateEntityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(StateDomainList, StateEntityPage.getPageable(), StateEntityPage.getTotalElements());
    }
}
