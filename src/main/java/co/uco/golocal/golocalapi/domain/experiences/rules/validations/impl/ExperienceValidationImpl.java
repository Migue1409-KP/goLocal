package co.uco.golocal.golocalapi.domain.experiences.rules.validations.impl;

import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.experiences.rules.validations.ExperienceValidateDescription;
import co.uco.golocal.golocalapi.domain.experiences.rules.validations.ExperienceValidateName;
import org.springframework.stereotype.Service;

@Service
public class ExperienceValidationImpl {
    private ExperienceValidateName experienceValidateName;
    private ExperienceValidateDescription experienceValidateDescription;

    public ExperienceValidationImpl(ExperienceValidateName experienceValidateName,
                                    ExperienceValidateDescription experienceValidateDescription) {
        this.experienceValidateName = experienceValidateName;
        this.experienceValidateDescription = experienceValidateDescription;
    }

    public void validations(ExperienceDomain experienceDomain) {
        experienceValidateName.execute(experienceDomain);
        experienceValidateDescription.execute(experienceDomain);
    }
}
