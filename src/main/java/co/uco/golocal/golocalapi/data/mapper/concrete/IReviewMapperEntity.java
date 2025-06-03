package co.uco.golocal.golocalapi.data.mapper.concrete;

import co.uco.golocal.golocalapi.data.entity.experience.ExperienceEntity;
import co.uco.golocal.golocalapi.data.entity.review.ReviewEntity;
import co.uco.golocal.golocalapi.data.entity.route.RouteEntity;
import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.experiences.ExperienceDomain;
import co.uco.golocal.golocalapi.domain.review.ReviewDomain;
import co.uco.golocal.golocalapi.domain.route.RouteDomain;
import co.uco.golocal.golocalapi.domain.user.UserDomain;
import org.mapstruct.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring", uses = {IUserMapperEntity.class, IExperienceMapperEntity.class, IRouteMapperEntity.class})
public interface IReviewMapperEntity {

    @Mapping(target = "route", source = "route", qualifiedByName = "mapRouteReference")
    @Mapping(target = "experience", source = "experience", qualifiedByName = "mapExperienceReference")
    @Mapping(target = "user", source = "user", qualifiedByName = "mapUserReference")
    ReviewEntity toEntity(ReviewDomain reviewDomain);

    ReviewDomain toDomain(ReviewEntity entity);

    List<ReviewDomain> toDomainList(List<ReviewEntity> entityList);

    default Page<ReviewDomain> toDomainPage(Page<ReviewEntity> entityPage) {
        List<ReviewDomain> domains = entityPage.getContent()
                .stream()
                .map(this::toDomain)
                .collect(Collectors.toList());
        return new PageImpl<>(domains, entityPage.getPageable(), entityPage.getTotalElements());
    }

    @Named("mapRouteReference")
    default RouteEntity mapRouteReference(RouteDomain route) {
        if (route == null || route.getId() == null) {
            return null;
        }
        RouteEntity routeEntity = new RouteEntity();
        routeEntity.setId(route.getId());
        return routeEntity;
    }

    @Named("mapExperienceReference")
    default ExperienceEntity mapExperienceReference(ExperienceDomain experience) {
        if (experience == null || experience.getId() == null) {
            return null;
        }
        ExperienceEntity experienceEntity = new ExperienceEntity();
        experienceEntity.setId(experience.getId());
        return experienceEntity;
    }

    @Named("mapUserReference")
    default UserEntity mapUserReference(UserDomain user) {
        if (user == null || user.getId() == null) {
            return null;
        }
        UserEntity userEntity = new UserEntity();
        userEntity.setId(user.getId());
        return userEntity;
    }
}