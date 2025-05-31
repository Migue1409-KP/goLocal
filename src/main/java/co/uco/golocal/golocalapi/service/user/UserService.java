package co.uco.golocal.golocalapi.service.user;

import co.uco.golocal.golocalapi.domain.user.UserDomain;

import java.util.List;
import java.util.UUID;

import co.uco.golocal.golocalapi.data.mapper.concrete.IUsuarioMapperEntity;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.impl.CreateUserUseCase;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.impl.DeleteUserUseCase;
import co.uco.golocal.golocalapi.domain.user.reglasdomain.impl.UpdateUserUseCase;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;

@Service
public class UserService {
	private final IUserRepository userRepository;
	private final IUsuarioMapperEntity userMapper;
	private final CreateUserUseCase createUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;
	private final DeleteUserUseCase deleteUserUseCase;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository userRepository, IUsuarioMapperEntity userMapper,
					   CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
					   DeleteUserUseCase deleteUserUseCase, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.createUserUseCase = createUserUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.deleteUserUseCase = deleteUserUseCase;
		this.passwordEncoder = passwordEncoder;
	}

	public UserDomain createUser(UserDomain user) {

		createUserUseCase.execute(user);

		user.setId(UUID.randomUUID());
		user.setPassword(passwordEncoder.encode(user.getPassword()));

		return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)));
	}

	public List<UserDomain> getAllUsers() {
		return  userRepository.findAll()
				.stream()
				.map(userMapper::toDomain)
				.toList();
	}

	public UserDomain getUserById(UUID id) {
		return userMapper.toDomain(userRepository.findById(id)
				.orElseThrow(() -> null));
	}

	public UserDomain updateUser(UserDomain user) {
		updateUserUseCase.execute(user);
		return userMapper.toDomain(userRepository.save(userMapper.toEntity(user)));
	}

	public void deleteUser(UUID id){
		deleteUserUseCase.execute(id);
	}
}
