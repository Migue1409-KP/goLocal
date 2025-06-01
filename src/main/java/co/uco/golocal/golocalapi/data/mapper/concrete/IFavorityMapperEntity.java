package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.user.FavorityEntity;
import co.uco.golocal.golocalapi.data.mapper.MapperEntity;
import co.uco.golocal.golocalapi.domain.user.FavorityDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFavorityMapperEntity extends MapperEntity<FavorityEntity, FavorityDomain> {
	@Override
	FavorityEntity toEntity(FavorityDomain domain);

	@Override
	FavorityDomain toDomain(FavorityEntity entity);
}
