package co.uco.golocal.golocalapi.category;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.ICategoryMapperEntity;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.category.categoriesrulesdomain.impl.DeleteCategoryUseCase;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import co.uco.golocal.golocalapi.service.category.CategoryService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class CategoryServiceTest {
    private ICategoryRepository categoryRepository;
    private ICategoryMapperEntity categoryMapper;
    private DeleteCategoryUseCase deleteUseCase;
    private IExperienceRepository experienceRepository;

    private CategoryService categoryService;

    @BeforeEach
    void setUp() {
        categoryRepository = mock(ICategoryRepository.class);
        categoryMapper = mock(ICategoryMapperEntity.class);
        deleteUseCase = mock(DeleteCategoryUseCase.class);
        experienceRepository = mock(IExperienceRepository.class);

        categoryService = new CategoryService(
                categoryRepository, categoryMapper, deleteUseCase, experienceRepository
        );
    }

    @Test
    void deleteCategory_withoutExperiences_shouldSucceed() {
        UUID id = UUID.randomUUID();
        when(experienceRepository.existsByCategoryId(id)).thenReturn(false);

        categoryService.deleteCategory(id);

        verify(deleteUseCase).execute(id);
        verify(categoryRepository).deleteById(id);
    }

    @Test
    void deleteCategory_withExperiences_shouldThrowException() {
        UUID id = UUID.randomUUID();
        when(experienceRepository.existsByCategoryId(id)).thenReturn(true);

        Exception exception = assertThrows(IllegalStateException.class, () ->
                categoryService.deleteCategory(id)
        );

        assertEquals("No se puede eliminar la categoría porque hay experiencias asociadas.", exception.getMessage());
        verify(categoryRepository, never()).deleteById(id);
    }

    @Test
    void getCategoryById_shouldReturnCategory() {
        UUID id = UUID.randomUUID();
        CategoryEntity entity = new CategoryEntity();
        CategoryDomain domain = new CategoryDomain();

        when(categoryRepository.findById(id)).thenReturn(Optional.of(entity));
        when(categoryMapper.toDomain(entity)).thenReturn(domain);

        CategoryDomain result = categoryService.getCategoryById(id);

        assertEquals(domain, result);
        verify(categoryRepository).findById(id);
    }
}
