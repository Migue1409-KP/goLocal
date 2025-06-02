package co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.ExistBussinestIdRule;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.ExistExperienceIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class UpdatePartialExperienceUseCase {
    private final ExistExperienceIdRule existExperienceIdRule;

    public UpdatePartialExperienceUseCase(ExistExperienceIdRule existExperienceIdRule) {
        this.existExperienceIdRule = existExperienceIdRule;
    }

    public void execute(UUID id) {
        existExperienceIdRule.execute(id);
    }
}

