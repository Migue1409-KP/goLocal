package co.uco.golocal.golocalapi.repository.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IBusinessRepository extends JpaRepository<BusinessEntity, UUID> {
    boolean existsByName(String name);
    boolean existsByBusinessId(UUID id);
    BusinessEntity findByName(String name);


}
