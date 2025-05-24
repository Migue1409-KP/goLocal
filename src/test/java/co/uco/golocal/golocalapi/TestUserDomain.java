package co.uco.golocal.golocalapi;

import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IUsuarioMapperEntity;
import co.uco.golocal.golocalapi.domain.usuario.UserDomain;
import co.uco.golocal.golocalapi.domain.usuario.reglas.validaciones.impl.ValidacionesUsuarioImpl;
import co.uco.golocal.golocalapi.domain.usuario.reglasdomain.impl.ReglasDominioUsuairo;
import co.uco.golocal.golocalapi.repository.usuario.IUsuarioRepositorio;
import co.uco.golocal.golocalapi.service.usuario.UserService;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Example;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@ExtendWith(MockitoExtension.class)
public class TestUserDomain {

    @Mock
    private PasswordEncoder passwordEncoder;
    @Mock
    private ValidacionesUsuarioImpl validacionesUsuario;
    @Mock
    private IUsuarioRepositorio usuarioRepositorio;
    @Mock
    private ReglasDominioUsuairo reglasDominioUsuairo;
    @Mock
    private IUsuarioMapperEntity iUsuarioMapperEntity;
    @InjectMocks
    private UserService userService;


    @Test
    public void testConsultarUsuarioPorId() {
        // Configuración del ID y el objeto de usuario
        UUID id = UUID.randomUUID();
        UsuarioEntity usuarioEsperado = new UsuarioEntity();
        usuarioEsperado.setId(id);
        // Configura el mock para que devuelva un Optional con el usuario cuando se llame findById
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuarioEsperado));
        // Llama al método del servicio
        var usuarioObtenido = userService.consultarUsuarioPorId(id);
        // Verifica que el resultado no sea nulo y que esté presente
        assertTrue(usuarioObtenido.isPresent(), "El usuario obtenido debería estar presente");
        assertEquals(usuarioEsperado, usuarioObtenido.get(), "El usuario obtenido debería ser el esperado");
        // Verifica que el método findById fue llamado con el ID correcto
        verify(usuarioRepositorio).findById(id);
    }


    @Test
    public void testEliminarUsuario() {
        UUID id = UUID.randomUUID();
        UsuarioEntity usuarioEsperado = new UsuarioEntity();
        usuarioEsperado.setId(id);
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuarioEsperado));
        userService.delete(id);
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.empty());
        var usuarioObtenido = userService.consultarUsuarioPorId(id);
        assertTrue(usuarioObtenido.isEmpty(), "El usuario obtenido no esta presente");
        assertNotEquals(usuarioEsperado, usuarioObtenido.orElse(null), "estos dos no son iguales");
        verify(usuarioRepositorio, times(2)).findById(id);
        verify(usuarioRepositorio).deleteById(id);
    }

    @Test
    public void testActualizarUsuarioConsPut() {
        UUID id = UUID.randomUUID();
        UserDomain usuarioDomain = new UserDomain(id, "juano", "guzman", "cc", "4567891236",
                "dualipa", "ochoanei@gmail.com", "Jefe", "cartagena", "carrera tren", "jajajja@gmail.com", "1234567894");
        UsuarioEntity usuarioEsperado = new UsuarioEntity();
        usuarioEsperado.setId(id);
        usuarioEsperado.setNombre("juano");
        usuarioEsperado.setContrasena("contraseñaCodificada");
        when(passwordEncoder.encode(any(CharSequence.class))).thenReturn("contraseñaCodificada");
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuarioEsperado));
        // Mock del mapper para convertir de Usuario a UsuarioEntity
        when(iUsuarioMapperEntity.toEntity(any(UserDomain.class))).thenAnswer(invocation -> {
            UserDomain usuarioDomainArgumento = invocation.getArgument(0);
            UsuarioEntity usuarioEntity = new UsuarioEntity();
            usuarioEntity.setId(usuarioDomainArgumento.getId());
            usuarioEntity.setNombre(usuarioDomainArgumento.getNombre());  // Aquí aplicamos los cambios
            usuarioEntity.setContrasena(passwordEncoder.encode(usuarioDomainArgumento.getContrasena()));
            return usuarioEntity;
        });
        // Consultamos el usuario antes de la actualización
        var usuarioObtenido = userService.consultarUsuarioPorId(id);
        UsuarioEntity usuarioObtenido2 = usuarioObtenido.get();
        // Actualizamos el usuario
        userService.actualizarUsuario(usuarioDomain);
        // Simulamos la recuperación del usuario actualizado
        UsuarioEntity usuarioActualizado = new UsuarioEntity();
        usuarioActualizado.setId(id);
        usuarioActualizado.setNombre("dualipa"); // Cambiamos el nombre tras la actualización
        usuarioActualizado.setContrasena("contraseñaCodificada"); // Contraseña codificada
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuarioActualizado));
        // Consultamos el usuario después de la actualización
        var usuarioEditado = userService.consultarUsuarioPorId(id);
        UsuarioEntity usuarioEditado2 = usuarioEditado.get();
        // Verificación
        assertNotEquals(usuarioObtenido2.getNombre(), usuarioEditado2.getNombre(), "Los nombres de los usuarios no deberían ser iguales");
        assertEquals("dualipa", usuarioEditado2.getNombre(), "El nombre del usuario actualizado debería ser 'dualipa'");
    }

    @Test
    public void testActualizarParcial() {
        // Datos para la prueba
        UUID id = UUID.randomUUID();
        Map<String, Object> cambios = Map.of("nombre", "dualipa", "correo", "nuevocorreo@gmail.com");

        // Simulando un usuario existente en la base de datos
        UsuarioEntity usuarioEntity = new UsuarioEntity();
        usuarioEntity.setId(id);
        usuarioEntity.setNombre("juanito");
        usuarioEntity.setCorreo("correoanterior@gmail.com");

        // Simulando el mapeo de UsuarioEntity a Usuario
        UserDomain usuarioDomainMapeado = new UserDomain();
        usuarioDomainMapeado.setId(id);
        usuarioDomainMapeado.setNombre("juanito");
        usuarioDomainMapeado.setCorreo("correoanterior@gmail.com");

        // Mockeando el repositorio y el mapeador
        when(usuarioRepositorio.findById(id)).thenReturn(Optional.of(usuarioEntity));
        when(iUsuarioMapperEntity.toDomain(usuarioEntity)).thenReturn(usuarioDomainMapeado);
        when(iUsuarioMapperEntity.toEntity(any(UserDomain.class))).thenAnswer(invocation -> {
            UserDomain usuarioDomain = invocation.getArgument(0);
            UsuarioEntity entity = new UsuarioEntity();
            entity.setId(usuarioDomain.getId());
            entity.setNombre(usuarioDomain.getNombre());
            entity.setCorreo(usuarioDomain.getCorreo());
            return entity;
        });

        // Ejecutar el método que estamos probando
        UserDomain usuarioDomainActualizado = userService.actualizarParcial(id, cambios);

        // Verificar que el nombre y el correo hayan cambiado
        assertEquals("dualipa", usuarioDomainActualizado.getNombre(), "El nombre debería haber sido actualizado a 'dualipa'");
        assertEquals("nuevocorreo@gmail.com", usuarioDomainActualizado.getCorreo(), "El correo debería haber sido actualizado a 'nuevocorreo@gmail.com'");

        // Verificar que el método save haya sido llamado en el repositorio
        verify(usuarioRepositorio, times(1)).save(any(UsuarioEntity.class));
    }
    @Test
    public void testConsultarUsuario() {
        // Crear un usuario de dominio y una lista de entidades
        UserDomain usuarioDomain = new UserDomain();
        UsuarioEntity usuarioEntidad = new UsuarioEntity();
        List<UsuarioEntity> listaUsuarios = List.of(usuarioEntidad);

        // Configurar el mock del mapeador
        when(iUsuarioMapperEntity.toEntity(usuarioDomain)).thenReturn(usuarioEntidad);

        // Configurar el mock del repositorio
        when(usuarioRepositorio.findAll(any(Example.class))).thenReturn(listaUsuarios);

        // Llamar al método a probar
        List<UsuarioEntity> resultado = userService.consultarUsuario(usuarioDomain);

        // Verificaciones
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        verify(usuarioRepositorio).findAll(any(Example.class));
    }

    @Test
    public void testRegistrarUsuario() {
        // Datos de usuario en el dominio para la prueba
        UserDomain usuarioDomain = new UserDomain();
        usuarioDomain.setNombre("dualipa");
        usuarioDomain.setContrasena("password123");

        // Simulación del mapeador de dominio a entidad
        UsuarioEntity usuarioEntidad = new UsuarioEntity();
        usuarioEntidad.setNombre("dualipa");
        usuarioEntidad.setContrasena("password123");

        // Mock del mapeador para convertir de dominio a entidad
        when(iUsuarioMapperEntity.toEntity(usuarioDomain)).thenReturn(usuarioEntidad);

        // Mock del PasswordEncoder para simular la codificación de la contraseña
        when(passwordEncoder.encode("password123")).thenReturn("passwordEncriptado");

        // Ejecutar el método registrarUsuario
        userService.registrarUsuario(usuarioDomain);

        // Verificar que las validaciones fueron llamadas
        verify(validacionesUsuario, times(1)).validaciones(usuarioDomain);
        verify(reglasDominioUsuairo, times(1)).validacionReglasDominio(usuarioDomain);

        // Verificar que la contraseña fue encriptada y actualizada
        assertEquals("passwordEncriptado", usuarioEntidad.getContrasena(), "La contraseña debe estar encriptada");

        // Verificar que se generó un UUID para el ID del usuario
        assertNotNull(usuarioEntidad.getId(), "El ID del usuario no debe ser nulo");

        // Verificar que el método save fue llamado para guardar el usuario en el repositorio
        verify(usuarioRepositorio, times(1)).save(usuarioEntidad);
    } 
}


