package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ICategoryMapperEntity {

    CategoryEntity toEntity(CategoryDomain domain);
    CategoryDomain toDomain(CategoryEntity entity);

    default Page<CategoryDomain> CategoryEntityPageToCategoryPage(Page<CategoryEntity> categoryEntityPage) {
        List<CategoryDomain> domainList = categoryEntityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(domainList, categoryEntityPage.getPageable(), categoryEntityPage.getTotalElements());
    }
}
