package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.user.FavoriteEntity;
import co.uco.golocal.golocalapi.data.mapper.MapperEntity;
import co.uco.golocal.golocalapi.domain.user.FavoriteDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IFavorityMapperEntity extends MapperEntity<FavoriteEntity, FavoriteDomain> {
	@Override
    FavoriteEntity toEntity(FavoriteDomain domain);

	@Override
	FavoriteDomain toDomain(FavoriteEntity entity);
}
