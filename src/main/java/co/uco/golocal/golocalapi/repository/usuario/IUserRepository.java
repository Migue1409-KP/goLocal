package co.uco.golocal.golocalapi.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.uco.golocal.golocalapi.data.entity.usuario.UsuarioEntity;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UsuarioEntity, UUID>{

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByTaxId(String taxId);
    UsuarioEntity findByEmail(String email);
    boolean existsById(UUID id);

}
