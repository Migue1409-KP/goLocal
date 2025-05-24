package co.uco.golocal.golocalapi.service.user;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.reglas.validaciones.impl.ValidacionesUsuarioImpl;

import java.util.UUID;

import co.uco.golocal.golocalapi.data.mapper.concrete.IUsuarioMapperEntity;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.impl.ReglasDominioUsuairo;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;

@Service
public class UserService {
	private final IUserRepository usuarioRepositorio;
	private final IUsuarioMapperEntity usuarioMapeadorEntidad;
	private final ValidacionesUsuarioImpl validacionesUsuario;
	private final ReglasDominioUsuairo reglasDominioUsuairo;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository usuarioRepositorio, IUsuarioMapperEntity usuarioMapeadorEntidad,
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

		UserEntity usuarioEntidad = usuarioMapeadorEntidad.toEntity(usuarioDomain);

		usuarioEntidad.setId(UUID.randomUUID());

		var contraseniaEncriptada = passwordEncoder.encode(usuarioEntidad.getPassword());

		usuarioEntidad.setPassword(contraseniaEncriptada);

		usuarioRepositorio.save(usuarioEntidad);
	}
}
