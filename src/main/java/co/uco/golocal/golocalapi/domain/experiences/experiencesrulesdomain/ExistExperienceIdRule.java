package co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain;

import co.uco.golocal.golocalapi.domain.business.exception.DoesntBussinesExistIdException;
import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExisExperienceIdRule {
    private final IExperienceRepository iExperienceRepository;

    public ExisExperienceIdRule(IExperienceRepository iexperienceRepository) {
        this.iExperienceRepository = iexperienceRepository;
    }

    public void execute(UUID id) {
        if (!iExperienceRepository.existsById(id)) {
            throw new DoesntBussinesExistIdException("Business ID: " + id+ " does not exist.");
        }
    }


}
