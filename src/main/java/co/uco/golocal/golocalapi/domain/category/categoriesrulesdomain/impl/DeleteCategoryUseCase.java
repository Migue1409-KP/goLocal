package co.uco.golocal.golocalapi.domain.category.categoriesrulesdomain.impl;

import co.uco.golocal.golocalapi.domain.category.categoriesrulesdomain.ExistCategoryIdRule;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DeleteCategoryUseCase {
    private final ExistCategoryIdRule existCategoryIdRule;

    public DeleteCategoryUseCase(ExistCategoryIdRule existCategoryIdRule) {
        this.existCategoryIdRule = existCategoryIdRule;
    }

    public void execute(UUID id) {
        existCategoryIdRule.execute(id);
    }
}
