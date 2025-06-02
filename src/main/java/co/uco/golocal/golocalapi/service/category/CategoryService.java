package co.uco.golocal.golocalapi.service.category;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.ICategoryMapperEntity;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class CategoryService {
    private final ICategoryRepository categoryRepository;
    private final ICategoryMapperEntity categoryMapper;

    public CategoryService(ICategoryRepository categoryRepository,
                           ICategoryMapperEntity categoryMapper) {
        this.categoryRepository = categoryRepository;
        this.categoryMapper = categoryMapper;
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
}
