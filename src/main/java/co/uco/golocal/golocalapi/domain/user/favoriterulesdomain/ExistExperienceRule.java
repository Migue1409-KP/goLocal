package co.uco.golocal.golocalapi.domain.user.favoriterulesdomain;

import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistExperienceRule {
    private final IExperienceRepository iExperienceRepository;

    public ExistExperienceRule(IExperienceRepository iExperienceRepository) {
        this.iExperienceRepository = iExperienceRepository;
    }

    public void execute(UUID experienceId) {
        if (!iExperienceRepository.existsById(experienceId)) {
            throw new IllegalArgumentException("Experience id: " + experienceId + " does not exist");
        }
    }
}
