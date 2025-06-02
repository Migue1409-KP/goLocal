package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.category.dto.CategoryDTO;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface ICategoryMapperDTO {

    CategoryDomain toDomain(CategoryDTO categoryDTO);
}
