package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.experience.dto.ExperienceRequestDTO;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring")
public interface IExperienceMapperDTO {


    ExperienceDomain toDomain(ExperienceRequestDTO dto);

}
