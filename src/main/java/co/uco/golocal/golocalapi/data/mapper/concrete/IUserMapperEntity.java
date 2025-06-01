package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import org.mapstruct.Mapper;

import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IUserMapperEntity {

	IUserMapperEntity INSTANCE= Mappers.getMapper(IUserMapperEntity.class);

	UserEntity toEntity(UserDomain usuarioDomain);
	UserDomain toDomain(UserEntity entidad);

	default Page<UserDomain> UsuarioEntidadPageToUsuarioPage(Page<UserEntity> usuarioEntidadPage) {
		List<UserDomain> usuarioDomainList = usuarioEntidadPage.getContent()
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
		return new PageImpl<>(usuarioDomainList, usuarioEntidadPage.getPageable(), usuarioEntidadPage.getTotalElements());
	}

}
