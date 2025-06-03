package co.uco.golocal.golocalapi.repository.usuario;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
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
    @Transactional
    @Modifying
    @Query("UPDATE UserEntity u SET u.password = :password WHERE u.id = :id")
    void updatePasswordById(@Param("id") UUID id, @Param("password") String password);
}
