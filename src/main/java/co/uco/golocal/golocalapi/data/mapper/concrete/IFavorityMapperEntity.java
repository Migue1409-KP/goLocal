package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.user.FavorityEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.data.mapper.MapperEntity;
import co.uco.golocal.golocalapi.domain.user.FavorityDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IFavorityMapperEntity extends MapperEntity<FavorityEntity, FavorityDomain> {
	IFavorityMapperEntity INSTANCE = Mappers.getMapper(IFavorityMapperEntity.class);

	@Override
	FavorityEntity toEntity(FavorityDomain domain);

	@Override
	FavorityDomain toDomain(FavorityEntity entity);
}
