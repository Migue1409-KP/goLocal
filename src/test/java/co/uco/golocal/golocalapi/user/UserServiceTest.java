package co.uco.golocal.golocalapi.user;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IUserMapperEntity;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.CreateUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.DeleteUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.UpdatePasswordUserUseCase;
import co.uco.golocal.golocalapi.domain.user.userrulesdomain.impl.UpdateUserUseCase;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import co.uco.golocal.golocalapi.service.user.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserServiceTest {
    @Mock
    private IUserRepository userRepository;

    @Mock
    private IUserMapperEntity userMapper;

    @Mock
    private CreateUserUseCase createUserUseCase;

    @Mock
    private UpdateUserUseCase updateUserUseCase;

    @Mock
    private DeleteUserUseCase deleteUserUseCase;

    @Mock
    private UpdatePasswordUserUseCase updatePasswordUserUseCase;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private UserService userService;

    private UserDomain userDomain;
    private UserEntity userEntity;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        UUID userId = UUID.randomUUID();
        userDomain = new UserDomain();
        userDomain.setId(userId);
        userDomain.setName("John");
        userDomain.setEmail("john@example.com");
        userDomain.setPassword("1234");

        userEntity = new UserEntity();
        userEntity.setId(userId);
        userEntity.setName("John");
        userEntity.setEmail("john@example.com");
        userEntity.setPassword("hashed_password");
    }

    @Test
    void testCreateUser() {
        when(passwordEncoder.encode(anyString())).thenReturn("hashed_password");
        when(userMapper.toEntity(any())).thenReturn(userEntity);
        when(userRepository.save(any())).thenReturn(userEntity);
        when(userMapper.toDomain(any())).thenReturn(userDomain);

        UserDomain result = userService.createUser(userDomain);

        assertNotNull(result);
        assertEquals(userDomain.getEmail(), result.getEmail());
        verify(createUserUseCase).execute(userDomain);
        verify(userRepository).save(userEntity);
    }

    @Test
    void testGetAllUsers() {
        List<UserEntity> entities = List.of(userEntity);
        List<UserDomain> domains = List.of(userDomain);

        when(userRepository.findAll()).thenReturn(entities);
        when(userMapper.toDomain(userEntity)).thenReturn(userDomain);

        List<UserDomain> result = userService.getAllUsers();

        assertEquals(1, result.size());
        assertEquals(userDomain.getName(), result.get(0).getName());
        verify(userRepository).findAll();
    }

    @Test
    void testGetUserById() {
        when(userRepository.findById(userDomain.getId())).thenReturn(Optional.of(userEntity));
        when(userMapper.toDomain(userEntity)).thenReturn(userDomain);

        UserDomain result = userService.getUserById(userDomain.getId());

        assertNotNull(result);
        assertEquals(userDomain.getEmail(), result.getEmail());
        verify(userRepository).findById(userDomain.getId());
    }
}
