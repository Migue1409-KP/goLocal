package co.uco.golocal.golocalapi.service.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IBusinessMapperEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.BusinesRulesDomain;
import co.uco.golocal.golocalapi.domain.business.rules.validations.impl.BusinessValidationImpl;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;


@Service
public class BusinessService {
    private final IBusinessRepository businessRepository;
    private final IBusinessMapperEntity businessMapperEntity;
    private final BusinessValidationImpl businessValidation;
    private final BusinesRulesDomain businessRulesDomain;



    public BusinessService(IBusinessRepository businessRepository, IBusinessMapperEntity businessMapperEntity,
                           BusinessValidationImpl businessValidation, BusinesRulesDomain businessRulesDomain) {
        this.businessRepository = businessRepository;
        this.businessMapperEntity = businessMapperEntity;
        this.businessValidation = businessValidation;
        this.businessRulesDomain = businessRulesDomain;
    }
    public void createBusiness(BusinessDomain businessDomain){
        businessValidation.validations(businessDomain);
        businessRulesDomain.BusinessvalidationDomainRules(businessDomain);
        BusinessEntity businessEntity = businessMapperEntity.toEntity(businessDomain);
        businessRepository.save(businessEntity);
    }

    public Optional<BusinessEntity> getBusinessById(UUID id) {
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
        Optional<BusinessEntity> optionalBusinessEntity= businessRepository.findById(id);
        BusinessEntity businessEntity = optionalBusinessEntity.get();
        var businessMapping = businessMapperEntity.toDomain(businessEntity);
        updates.forEach((key,value)->{
            Field updatedField = ReflectionUtils.findField(BusinessDomain.class, key);
            if (updatedField != null) {
                updatedField.setAccessible(true);
                ReflectionUtils.setField(updatedField, businessMapping, value);
            }
        });
        BusinessEntity updatedBusiness= businessMapperEntity.toEntity(businessMapping);
        businessRepository.save(updatedBusiness);
        return businessMapping;
    }

    public Page<BusinessDomain> getAllBusinesses(Pageable pageable) {
        Page<BusinessEntity> entitiesBusiness = businessRepository.findAll(pageable);
        return entitiesBusiness.map(businessMapperEntity::toDomain);
    }
}
