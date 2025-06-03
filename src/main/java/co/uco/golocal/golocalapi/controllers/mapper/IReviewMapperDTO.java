package co.uco.golocal.golocalapi.controllers.mapper;

import co.uco.golocal.golocalapi.controllers.review.dto.ReviewDTO;
import co.uco.golocal.golocalapi.domain.review.ReviewDomain;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface IReviewMapperDTO {

    @Mapping(source = "userId", target = "user.id")
    @Mapping(source = "experienceId", target = "experience.id")
    @Mapping(source = "routeId", target = "route.id")
    ReviewDomain toDomain(ReviewDTO dto);

    @Mapping(source = "user.id", target = "userId")
    @Mapping(source = "experience.id", target = "experienceId")
    @Mapping(source = "route.id", target = "routeId")
    ReviewDTO toDTO(ReviewDomain domain);

    List<ReviewDomain> toDomainList(List<ReviewDTO> dtoList);

    List<ReviewDTO> toDTOList(List<ReviewDomain> domainList);

    default Page<ReviewDTO> toDTOPage(Page<ReviewDomain> domainPage) {
        List<ReviewDTO> dtoList = domainPage.getContent().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
        return new PageImpl<>(dtoList, domainPage.getPageable(), domainPage.getTotalElements());
    }
}