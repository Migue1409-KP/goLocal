package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import org.mapstruct.Mapper;

import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;
import org.mapstruct.factory.Mappers;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IUsuarioMapperEntity {

	IUsuarioMapperEntity INSTANCE= Mappers.getMapper(IUsuarioMapperEntity.class);

	UsuarioEntity toEntity(UserDomain usuarioDomain);
	UserDomain toDomain(UsuarioEntity entidad);

	default Page<UserDomain> UsuarioEntidadPageToUsuarioPage(Page<UsuarioEntity> usuarioEntidadPage) {
		List<UserDomain> usuarioDomainList = usuarioEntidadPage.getContent()
				.stream()
				.map(this::toDomain)
				.collect(Collectors.toList());
		return new PageImpl<>(usuarioDomainList, usuarioEntidadPage.getPageable(), usuarioEntidadPage.getTotalElements());
	}

}
