package co.uco.golocal.golocalapi.repository.experience;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface IExperienceRepository extends JpaRepository<ExperienceEntity, UUID> {
}
