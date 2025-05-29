package co.uco.golocal.golocalapi.repository.category;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ICategoryRepository extends JpaRepository<CategoryEntity, UUID>{
    boolean existsByName(String name);
}
