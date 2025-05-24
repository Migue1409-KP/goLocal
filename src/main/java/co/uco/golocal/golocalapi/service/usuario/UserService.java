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
public class UserService {
	private final IUsuarioRepositorio usuarioRepositorio;
	private final IUsuarioMapperEntity usuarioMapeadorEntidad;
	private final ValidacionesUsuarioImpl validacionesUsuario;
	private final ReglasDominioUsuairo reglasDominioUsuairo;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUsuarioRepositorio usuarioRepositorio, IUsuarioMapperEntity usuarioMapeadorEntidad,
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

		var contraseniaEncriptada = passwordEncoder.encode(usuarioEntidad.getPassword());

		usuarioEntidad.setPassword(contraseniaEncriptada);

		usuarioRepositorio.save(usuarioEntidad);
	}
}
