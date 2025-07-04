package co.uco.golocal.golocalapi.service.user;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IUserMapperEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;

import java.util.List;
import java.util.UUID;

import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.CreateUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.DeleteUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.UpdatePasswordUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.UpdateUserUseCase;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;

@Service
public class UserService {
	private final IUserRepository userRepository;
	private final IUserMapperEntity userMapper;
	private final CreateUserUseCase createUserUseCase;
	private final UpdateUserUseCase updateUserUseCase;
	private final UpdatePasswordUserUseCase updatePasswordUserUseCase;
	private final DeleteUserUseCase deleteUserUseCase;
	private final PasswordEncoder passwordEncoder;

	public UserService(IUserRepository userRepository, IUserMapperEntity userMapper,
					   CreateUserUseCase createUserUseCase, UpdateUserUseCase updateUserUseCase,
					   DeleteUserUseCase deleteUserUseCase, PasswordEncoder passwordEncoder,
					   UpdatePasswordUserUseCase updatePasswordUserUseCase) {
		this.userRepository = userRepository;
		this.userMapper = userMapper;
		this.createUserUseCase = createUserUseCase;
		this.updateUserUseCase = updateUserUseCase;
		this.deleteUserUseCase = deleteUserUseCase;
		this.passwordEncoder = passwordEncoder;
		this.updatePasswordUserUseCase = updatePasswordUserUseCase;
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
		UserEntity userToUpdate = updateUserUseCase.execute(user);
		return userMapper.toDomain(userRepository.save(userToUpdate));
	}

	@Transactional
	public boolean updatePassword(UUID id, String newPassword) {
		updatePasswordUserUseCase.execute(id);
		userRepository.updatePasswordById(id, passwordEncoder.encode(newPassword));
		return true;
	}

	public void deleteUser(UUID id){
		deleteUserUseCase.execute(id);
		userRepository.deleteById(id);
	}
}
