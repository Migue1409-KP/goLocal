package co.uco.golocal.golocalapi.experiences;


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
import co.uco.golocal.golocalapi.service.experience.ExperienceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

 class ExperienceServiceTest {
    @InjectMocks
    private ExperienceService experienceService;

    @Mock
    private IExperienceRepository experienceRepository;
    @Mock
    private IExperienceMapperEntity experienceMapperEntity;
    @Mock
    private DeleteExperienceUseCase deleteExperienceUseCase;
    @Mock
    private GetExperienceByIdUseCase getExperienceByIdUseCase;
    @Mock
    private UpdatePartialExperienceUseCase updatePartialExperienceUseCase;
    @Mock
    private IBusinessRepository businessRepository;
    @Mock
    private ICategoryRepository categoryRepository;
    @Mock
    private IFavoriteRepository iFavoriteRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateExperience_success() {
        UUID businessId = UUID.randomUUID();
        UUID categoryId = UUID.randomUUID();

        ExperienceDomain domain = ExperienceDomain.builder()
                .name("Cena Romántica")
                .description("Cena con velas en la terraza")
                .price(100.0)
                .businessId(businessId)
                .categoryId(categoryId)
                .build();

        ExperienceEntity entity = new ExperienceEntity();
        when(experienceMapperEntity.toEntity(domain)).thenReturn(entity);
        when(businessRepository.findById(businessId)).thenReturn(Optional.of(new BusinessEntity()));
        when(categoryRepository.findById(categoryId)).thenReturn(Optional.of(new CategoryEntity()));

        experienceService.createExperience(domain);

        verify(experienceRepository, times(1)).save(entity);
    }

    @Test
    void testGetAllExperiencesInList_returnsMappedList() {
        ExperienceEntity entity1 = new ExperienceEntity();
        ExperienceEntity entity2 = new ExperienceEntity();

        ExperienceDomain domain1 = ExperienceDomain.builder().name("Exp 1").build();
        ExperienceDomain domain2 = ExperienceDomain.builder().name("Exp 2").build();

        when(experienceRepository.findAll()).thenReturn(List.of(entity1, entity2));
        when(experienceMapperEntity.toDomain(entity1)).thenReturn(domain1);
        when(experienceMapperEntity.toDomain(entity2)).thenReturn(domain2);

        List<ExperienceDomain> result = experienceService.getAllExperiencesInList();

        assertEquals(2, result.size());
        assertEquals("Exp 1", result.get(0).getName());
        assertEquals("Exp 2", result.get(1).getName());
    }

    @Test
    void testDeleteExperience_shouldDeleteExperienceAndFavorites() {
        UUID experienceId = UUID.randomUUID();

        ExperienceEntity experience = new ExperienceEntity();
        experience.setId(experienceId);

        FavoriteEntity favorite1 = new FavoriteEntity();
        favorite1.setExperience(experience);

        FavoriteEntity favorite2 = new FavoriteEntity();
        favorite2.setExperience(experience);

        List<FavoriteEntity> allFavorites = List.of(favorite1, favorite2);

        // Mock: Todos los favoritos existentes
        when(iFavoriteRepository.findAll()).thenReturn(allFavorites);

        // Act
        experienceService.deleteExperience(experienceId);

        // Assert
        verify(deleteExperienceUseCase).execute(experienceId);
        verify(iFavoriteRepository).deleteAll(List.of(favorite1, favorite2));
        verify(experienceRepository).deleteById(experienceId);
    }

}

