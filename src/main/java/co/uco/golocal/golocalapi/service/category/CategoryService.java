package co.uco.golocal.golocalapi.service.category;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.ICategoryMapperEntity;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.domain.category.categoriesrulesdomain.impl.DeleteCategoryUseCase;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import co.uco.golocal.golocalapi.repository.experience.IExperienceRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final ICategoryRepository categoryRepository;
    private final ICategoryMapperEntity categoryMapper;
    private final DeleteCategoryUseCase deleteCategoryUseCase;
    private final IExperienceRepository experienceRepository;

    public CategoryService(ICategoryRepository categoryRepository,
                           ICategoryMapperEntity categoryMapper, DeleteCategoryUseCase deleteCategoryUseCase, IExperienceRepository experienceRepository) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
        this.deleteCategoryUseCase = deleteCategoryUseCase;
        this.experienceRepository = experienceRepository;
    }

    public void createCategory(CategoryDomain categoryDomain) {
        CategoryEntity entity = categoryMapper.toEntity(categoryDomain);
        categoryRepository.save(entity);
    }

    public List<CategoryDomain> getAllCategories() {
        return categoryRepository.findAll()
                .stream()
                .map(categoryMapper::toDomain)
                .collect(Collectors.toList());
    }

    public CategoryDomain getCategoryById(UUID id) {
        return categoryRepository.findById(id)
                .map(categoryMapper::toDomain)
                .orElseThrow(() -> new RuntimeException("Category not found"));
    }

    public void deleteCategory(UUID id) {
        deleteCategoryUseCase.execute(id);
        if (experienceRepository.existsByCategoryId(id)) {
            throw new IllegalStateException("No se puede eliminar la categoría porque hay experiencias asociadas.");
        }
        categoryRepository.deleteById(id);
    }
}
