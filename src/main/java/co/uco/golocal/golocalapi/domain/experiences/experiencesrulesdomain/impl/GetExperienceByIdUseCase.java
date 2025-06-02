package co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.ExistBussinestIdRule;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.ExistExperienceIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class GetExperienceByIdUseCase {

    private final ExistExperienceIdRule existExperienceIdRule;

    public GetExperienceByIdUseCase(ExistExperienceIdRule existExperienceIdRule) {
        this.existExperienceIdRule = existExperienceIdRule;
    }

    public void execute(UUID id) {
        existExperienceIdRule.execute(id);
    }
}
