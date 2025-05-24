package co.uco.golocal.golocalapi.repository.usuario;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;

import java.util.UUID;

@Repository
public interface IUserRepository extends JpaRepository<UserEntity, UUID>{

    boolean existsByEmail(String email);
    boolean existsByPhone(String phone);
    boolean existsByTaxId(String taxId);
    UserEntity findByEmail(String email);
    boolean existsById(UUID id);

}
