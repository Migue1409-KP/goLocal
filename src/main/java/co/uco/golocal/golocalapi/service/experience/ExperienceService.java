package co.uco.golocal.golocalapi.service.experience;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.entity.user.FavoriteEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IExperienceMapperEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl.DeleteExperienceUseCase;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl.GetExperienceByIdUseCase;
import co.uco.golocal.golocalapi.domain.experiences.experiencesrulesdomain.impl.UpdatePartialExperienceUseCase;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import co.uco.golocal.golocalapi.repository.usuario.IFavoriteRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExperienceService {
    private final IExperienceRepository experienceRepository;
    private final IExperienceMapperEntity experienceMapperEntity;
    private final DeleteExperienceUseCase deleteExperienceUseCase;
    private final GetExperienceByIdUseCase getExperienceByIdUseCase;
    private final UpdatePartialExperienceUseCase updatePartialExperienceUseCase;
    private final IBusinessRepository businessRepository;
    private final ICategoryRepository categoryRepository;
    private final IFavoriteRepository iFavoriteRepository;
    private static final Set<String> ALLOWED_FIELDS = Set.of(
            "title", "description", "price"
    );


    public ExperienceService(IExperienceRepository experienceRepository, IExperienceMapperEntity experienceMapperEntity,
                             DeleteExperienceUseCase deleteExperienceUseCase, GetExperienceByIdUseCase getExperienceByIdUseCase,
                             UpdatePartialExperienceUseCase updatePartialExperienceUseCase, IBusinessRepository businessRepository, ICategoryRepository categoryRepository, IFavoriteRepository iFavoriteRepository) {
        this.experienceRepository = experienceRepository;
        this.experienceMapperEntity = experienceMapperEntity;
        this.deleteExperienceUseCase = deleteExperienceUseCase;
        this.getExperienceByIdUseCase = getExperienceByIdUseCase;
        this.updatePartialExperienceUseCase = updatePartialExperienceUseCase;
        this.businessRepository = businessRepository;
        this.categoryRepository = categoryRepository;
        this.iFavoriteRepository = iFavoriteRepository;
    }

    public void createExperience(ExperienceDomain experienceDomain) {
        ExperienceEntity experienceEntity = experienceMapperEntity.toEntity(experienceDomain);
        BusinessEntity business = businessRepository.findById(experienceDomain.getBusinessId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado"));
        CategoryEntity category = categoryRepository.findById(experienceDomain.getCategoryId())
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        experienceEntity.setBusiness(business);
        experienceEntity.setCategory(category);
        experienceRepository.save(experienceEntity);
    }

    public Optional<ExperienceEntity> getExperienceById(UUID id) {
        getExperienceByIdUseCase.execute(id);
        return experienceRepository.findById(id);
    }


    public List<ExperienceDomain> getAllExperiencesInList() {
        return experienceRepository.findAll().stream()
                .map(experienceMapperEntity::toDomain)
                .collect(Collectors.toList());
    }

    public IExperienceMapperEntity getBusinessMapper() {
        return experienceMapperEntity;
    }


    public Page<ExperienceDomain> getAllExperiences(Pageable pageable) {
        Page<ExperienceEntity> experiencePage = experienceRepository.findAll(pageable);
        return experiencePage.map(experienceMapperEntity::toDomain);
    }

    public void deleteExperience(UUID id) {
        deleteExperienceUseCase.execute(id);
        List<FavoriteEntity> favoritos = iFavoriteRepository.findAll()
                .stream()
                .filter(fav -> fav.getExperience() != null && id.equals(fav.getExperience().getId()))
                .toList();
        iFavoriteRepository.deleteAll(favoritos);
        experienceRepository.deleteById(id);
    }

    public ExperienceDomain updatePartialExperience(UUID id, Map<String, Object> updates) {
        updatePartialExperienceUseCase.execute(id);
        Optional<ExperienceEntity> optionalExperience = experienceRepository.findById(id);
        ExperienceEntity experienceEntity=optionalExperience.get();
        var experienceMapping= experienceMapperEntity.toDomain(experienceEntity);
        updates.forEach((key, value) -> {
            if(ALLOWED_FIELDS.contains(key)) {
                Field updateField = ReflectionUtils.findField(ExperienceDomain.class, key);
                if (updateField != null) {
                    updateField.setAccessible(true);
                    ReflectionUtils.setField(updateField, experienceMapping, value);
                } else {
                    throw new IllegalArgumentException("Campo no permitido: " + key);
                }
            }
        });
        ExperienceEntity experience = experienceMapperEntity.toEntity(experienceMapping);
        experience.setFavorites(experienceEntity.getFavorites());
        experienceRepository.save(experience);
        return experienceMapping;
    }
}
