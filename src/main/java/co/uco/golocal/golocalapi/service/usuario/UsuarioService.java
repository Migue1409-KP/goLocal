package co.uco.golocal.golocalapi.service.usuario;

import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;
import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones.impl.ValidacionesUsuarioImpl;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import co.uco.golocal.golocalapi.data.mapper.concrete.IUsuarioMapperEntity;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import co.uco.golocal.golocalapi.domain.usuario.reglasdomain.impl.ReglasDominioUsuairo;
import org.springframework.data.domain.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.uco.golocal.golocalapi.repository.usuario.IUsuarioRepositorio;
import org.springframework.util.ReflectionUtils;

@Service
public class UsuarioService {
	private final IUsuarioRepositorio usuarioRepositorio;
	private final IUsuarioMapperEntity usuarioMapeadorEntidad;
	private final ValidacionesUsuarioImpl validacionesUsuario;
	private final ReglasDominioUsuairo reglasDominioUsuairo;
	private final PasswordEncoder passwordEncoder;

	public UsuarioService(IUsuarioRepositorio usuarioRepositorio, IUsuarioMapperEntity usuarioMapeadorEntidad,
						  ValidacionesUsuarioImpl validacionesUsuario, ReglasDominioUsuairo reglasDominioUsuairo,
						  PasswordEncoder passwordEncoder) {
		this.usuarioRepositorio = usuarioRepositorio;
		this.usuarioMapeadorEntidad = usuarioMapeadorEntidad;
		this.validacionesUsuario = validacionesUsuario;
		this.reglasDominioUsuairo = reglasDominioUsuairo;
		this.passwordEncoder = passwordEncoder;
	}

	public void registrarUsuario(UserDomain usuarioDomain) {

		validacionesUsuario.validaciones(usuarioDomain);

		reglasDominioUsuairo.validacionReglasDominio(usuarioDomain);

		UsuarioEntity usuarioEntidad = usuarioMapeadorEntidad.toEntity(usuarioDomain);

		usuarioEntidad.setId(UUID.randomUUID());

		var contraseniaEncriptada = passwordEncoder.encode(usuarioEntidad.getContrasena());

		usuarioEntidad.setContrasena(contraseniaEncriptada);

		usuarioRepositorio.save(usuarioEntidad);
	}

	public List<UsuarioEntity> consultarUsuario(UserDomain usuarioDomain) {

		UsuarioEntity usuarioEntidad = usuarioMapeadorEntidad.toEntity(usuarioDomain);

		ExampleMatcher matcher = ExampleMatcher.matching()
				.withIgnoreNullValues()
				.withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);

		Example<UsuarioEntity> example = Example.of(usuarioEntidad, matcher);

		return usuarioRepositorio.findAll(example);


	}

	public Optional<UsuarioEntity> consultarUsuarioPorId(UUID id) {
		return usuarioRepositorio.findById(id);
	}


	public void actualizarUsuario(UserDomain usuarioDomain) {
		validacionesUsuario.validaciones(usuarioDomain);
		UsuarioEntity usuarioEntidad = usuarioMapeadorEntidad.toEntity(usuarioDomain);
		Optional<UsuarioEntity> usuario = usuarioRepositorio.findById(usuarioDomain.getId());
		if (!usuario.isEmpty()) {
			var contraseniaEncriptada = passwordEncoder.encode(usuarioEntidad.getContrasena());
			usuarioEntidad.setContrasena(contraseniaEncriptada);
			usuarioRepositorio.save(usuarioEntidad);
		}
	}


	public void delete(UUID id) {
		Optional<UsuarioEntity> usuario = consultarUsuarioPorId(id);
		if (usuario.isPresent()) {
			usuarioRepositorio.deleteById(id);
		}
	}


	public Page<UserDomain> obtenerTodosLosUsuarios(Pageable paginacion) {
		Page<UsuarioEntity> listaUsuarioEntidad = usuarioRepositorio.findAll(paginacion);
		if (listaUsuarioEntidad.isEmpty()) {
			return Page.empty();
		} else {
			return usuarioMapeadorEntidad.UsuarioEntidadPageToUsuarioPage(listaUsuarioEntidad);
		}
	}


	public List<UsuarioEntity> obtenerTodosLosUsuariosSinPaginacion() {
		return usuarioRepositorio.findAll();
	}

	public UserDomain actualizarParcial(UUID id, Map<String, Object> cambios) {
		Optional<UsuarioEntity> usuarioOptional = usuarioRepositorio.findById(id);
		UsuarioEntity usuarioEntity = usuarioOptional.get();
		var usuarioMapeado = usuarioMapeadorEntidad.toDomain(usuarioEntity);
		cambios.forEach((campo, valor) -> {
			Field campoActualizar = ReflectionUtils.findField(UserDomain.class, campo);
			if (campoActualizar != null) {
				campoActualizar.setAccessible(true);
				ReflectionUtils.setField(campoActualizar, usuarioMapeado, valor);
			}
		});
		UsuarioEntity usuarioActualizado = usuarioMapeadorEntidad.toEntity(usuarioMapeado);
		usuarioRepositorio.save(usuarioActualizado);
		return usuarioMapeado;
	}
}
