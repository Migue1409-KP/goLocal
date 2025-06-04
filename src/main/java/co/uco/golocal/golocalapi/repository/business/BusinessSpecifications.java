package co.uco.golocal.golocalapi.repository.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.UUID;

public class BusinessSpecifications {
    public static Specification<BusinessEntity> filterByCity(UUID cityId) {
        return (root, query, criteriaBuilder) ->
                cityId == null ? null : criteriaBuilder.equal(root.get("location").get("id"), cityId);
    }

    public static Specification<BusinessEntity> filterByState(UUID stateId) {
        return (root, query, criteriaBuilder) ->
                stateId == null ? null : criteriaBuilder.equal(root.get("location").get("state").get("id"), stateId);
    }

    public static Specification<BusinessEntity> filterByCategories(List<UUID> categoryIds) {
        return (root, query, cb) -> {
            if (categoryIds == null || categoryIds.isEmpty()) return null;
            Join<BusinessEntity, CategoryEntity> join = root.join("categories", JoinType.INNER);
            return join.get("id").in(categoryIds);
        };
    }

    public static Specification<BusinessEntity> filterByName(String name) {
        return (root, query, criteriaBuilder) -> {
            if (name == null || name.isEmpty()) return null;
            return criteriaBuilder.like(criteriaBuilder.lower(root.get("name")) , "%" + name.toLowerCase() + "%");
        };
    }
}
