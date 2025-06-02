package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface IUserMapperEntity {

	UserEntity toEntity(UserDomain usuarioDomain);
	UserDomain toDomain(UserEntity entidad);
}
