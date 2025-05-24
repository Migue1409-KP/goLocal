package co.uco.golocal.golocalapi.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;

import java.util.UUID;

@Repository
public interface IUsuarioRepositorio extends JpaRepository<UsuarioEntity, UUID>{

    boolean existsByCorreo(String correo);
    boolean existsByNumeroContacto(String numeroContacto);
    boolean existsByIdentificacion(String identificacion);
    UsuarioEntity findByCorreo(String correo);
    boolean existsById(UUID id);

}
