package co.uco.golocal.golocalapi.domain.category.categoriesrulesdomain;

import co.uco.golocal.golocalapi.domain.business.exception.DoesntBussinesExistIdException;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistCategoryIdRule {
    private final ICategoryRepository iCategoryRepository;

    public ExistCategoryIdRule(ICategoryRepository iCategoryRepository) {
        this.iCategoryRepository = iCategoryRepository;
    }

    public void execute(UUID id) {
        if (!iCategoryRepository.existsById(id)) {
            throw new DoesntBussinesExistIdException("Category ID: " + id+ " does not exist.");
        }
    }


}
