package co.uco.golocal.golocalapi.service.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.entity.category.CategoryEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IBusinessMapperEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.CreateBusinesUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.DeleteBusinesUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.GetBusinessByIdUseCase;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.UpdatePartialBusinessUseCase;
import co.uco.golocal.golocalapi.domain.category.CategoryDomain;
import co.uco.golocal.golocalapi.repository.business.BusinessSpecifications;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import co.uco.golocal.golocalapi.repository.category.ICategoryRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.*;


@Service
public class BusinessService {
    private final IBusinessRepository businessRepository;
    private final IBusinessMapperEntity businessMapperEntity;
    private final DeleteBusinesUseCase deleteBussinesCase;
    private final CreateBusinesUseCase createBusinesUseCase;
    private final UpdatePartialBusinessUseCase updateBusinessUseCase;
    private final GetBusinessByIdUseCase getBusinessByIdUseCase;
    private final ICategoryRepository categoryRepository;
    private static final Set<String> CAMPOS_VALIDOS = Set.of(
            "name", "description", "location"
    );



    public BusinessService(IBusinessRepository businessRepository, IBusinessMapperEntity businessMapperEntity,
                           DeleteBusinesUseCase deleteBussinesCase, CreateBusinesUseCase createBusinesUseCase,
                           UpdatePartialBusinessUseCase updateBusinessUseCase, GetBusinessByIdUseCase getBusinessByIdUseCase, ICategoryRepository categoryRepository) {
        this.businessRepository = businessRepository;
        this.businessMapperEntity = businessMapperEntity;
        this.deleteBussinesCase = deleteBussinesCase;
        this.createBusinesUseCase = createBusinesUseCase;
        this.updateBusinessUseCase = updateBusinessUseCase;
        this.getBusinessByIdUseCase = getBusinessByIdUseCase;
        this.categoryRepository = categoryRepository;
    }

    public void createBusiness(BusinessDomain businessDomain){
        createBusinesUseCase.execute(businessDomain);
        BusinessEntity businessEntity = businessMapperEntity.toEntity(businessDomain);

        if (businessDomain.getCategories() != null && !businessDomain.getCategories().isEmpty()) {
            List<UUID> categoryIds = businessDomain.getCategories()
                    .stream()
                    .map(CategoryDomain::getId)
                    .toList();

            List<CategoryEntity> attachedCategories = categoryRepository.findAllById(categoryIds);
            businessEntity.setCategories(attachedCategories);
        }
        businessRepository.save(businessEntity);
    }


    public Optional<BusinessEntity> getBusinessById(UUID id) {
        getBusinessByIdUseCase.execute(id);
        return businessRepository.findById(id);
    }

    public IBusinessMapperEntity getBusinessMapper() {
        return businessMapperEntity;
    }


    public Optional<BusinessEntity> getBusinessByName(String name) {
        if (businessRepository.existsByName(name)) {
            return businessRepository.findByName(name);
        } else {
            return Optional.empty();
        }
    }

    public BusinessDomain updatePartial(UUID id, Map<String, Object> updates) {
        updateBusinessUseCase.execute(id);
        Optional<BusinessEntity> optionalBusinessEntity= businessRepository.findById(id);
        BusinessEntity businessEntity = optionalBusinessEntity.get();
        var businessMapping = businessMapperEntity.toDomain(businessEntity);
        updates.forEach((key,value)->{
            if (CAMPOS_VALIDOS.contains(key)) {
                Field updatedField = ReflectionUtils.findField(BusinessDomain.class, key);
                if (updatedField != null) {
                    updatedField.setAccessible(true);
                    ReflectionUtils.setField(updatedField, businessMapping, value);
                }else {
                    System.out.println("Campo no válido o no encontrado: " + key);
                }
            }
        });
        BusinessEntity updatedBusiness= businessMapperEntity.toEntity(businessMapping);
        updatedBusiness.setExperiences(businessEntity.getExperiences());
        businessRepository.save(updatedBusiness);
        return businessMapping;
    }

    public Page<BusinessDomain> getAllBusinesses(Pageable pageable) {
        Page<BusinessEntity> entitiesBusiness = businessRepository.findAll(pageable);
        return entitiesBusiness.map(businessMapperEntity::toDomain);
    }

    public void deleteBusiness(UUID id) {
        deleteBussinesCase.execute(id);
        BusinessEntity business = businessRepository.findById(id).get();
        if (business.getExperiences() != null) {
            business.getExperiences().size();
        }
        businessRepository.delete(business);
    }

    public Page<BusinessDomain> filterBusinesses(UUID cityId, UUID stateId, List<UUID> categoryIds,String name, Pageable pageable) {
        Specification<BusinessEntity> spec = Specification
                .where(BusinessSpecifications.filterByCity(cityId))
                .and(BusinessSpecifications.filterByState(stateId))
                .and(BusinessSpecifications.filterByCategories(categoryIds))
                .and(BusinessSpecifications.filterByName(name));

        return businessRepository.findAll(spec, pageable)
                .map(businessMapperEntity::toDomain);
    }


}
