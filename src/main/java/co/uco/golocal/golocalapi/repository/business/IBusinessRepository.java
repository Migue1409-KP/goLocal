package co.uco.golocal.golocalapi.repository.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface IBusinessRepository extends JpaRepository<BusinessEntity, UUID>,JpaSpecificationExecutor<BusinessEntity> {
    boolean existsByName(String name);
    boolean existsById(UUID id);
    Optional<BusinessEntity> findByName(String name);
    Page<BusinessEntity> findAll(Pageable pageable);


}
