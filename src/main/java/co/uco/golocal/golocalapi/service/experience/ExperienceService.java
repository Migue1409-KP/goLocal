package co.uco.golocal.golocalapi.service.experience;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IExperienceMapperEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl.ExperienceRulesDomain;
import co.uco.golocal.golocalapi.domain.experiences.rules.validations.impl.ExperienceValidationImpl;
import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class ExperienceService {
    private final IExperienceRepository experienceRepository;
    private final IExperienceMapperEntity experienceMapperEntity;
    private final ExperienceValidationImpl experienceValidation;
    private final ExperienceRulesDomain experienceRulesDomain;

    public ExperienceService(IExperienceRepository experienceRepository, IExperienceMapperEntity experienceMapperEntity,
                             ExperienceValidationImpl experienceValidation, ExperienceRulesDomain experienceRulesDomain) {
        this.experienceRepository = experienceRepository;
        this.experienceMapperEntity = experienceMapperEntity;
        this.experienceValidation = experienceValidation;
        this.experienceRulesDomain = experienceRulesDomain;
    }

    public void createExperience(ExperienceDomain experienceDomain) {
        experienceValidation.validations(experienceDomain);
        ExperienceEntity experienceEntity = experienceMapperEntity.toEntity(experienceDomain);
        experienceRepository.save(experienceEntity);
    }
    public Optional<ExperienceEntity> getExperienceById(UUID id) {
        return experienceRepository.findById(id);
    }
    public List<ExperienceDomain> getAllExperiences() {
        return experienceRepository.findAll().stream()
                .map(experienceMapperEntity::toDomain)
                .collect(Collectors.toList());
    }
    public IExperienceMapperEntity getBusinessMapper() {
        return experienceMapperEntity;
    }


}
