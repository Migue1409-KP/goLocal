package co.uco.golocal.golocalapi.service.business;

import co.uco.golocal.golocalapi.data.entity.business.BusinessEntity;
import co.uco.golocal.golocalapi.data.mapper.concrete.IBusinessMapperEntity;
import co.uco.golocal.golocalapi.domain.business.BusinessDomain;
import co.uco.golocal.golocalapi.domain.business.businessrulesdomain.impl.BusinesRulesDomain;
import co.uco.golocal.golocalapi.domain.business.rules.validations.impl.BusinessValidationImpl;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BusinessService {
    private final IBusinessRepository businessRepository;
    private final IBusinessMapperEntity businessMapperEntity;
    private final BusinessValidationImpl businessValidation;
    private final BusinesRulesDomain businessRulesDomain;



    public BusinessService(IBusinessRepository businessRepository, IBusinessMapperEntity bussinesMapperEntity,
                           BusinessValidationImpl businessValidation, BusinesRulesDomain businessRulesDomain) {
        this.businessRepository = businessRepository;
        this.businessMapperEntity = bussinesMapperEntity;
        this.businessValidation = businessValidation;
        this.businessRulesDomain = businessRulesDomain;
    }
    public void registerBusiness (BusinessDomain businessDomain){
        businessValidation.validations(businessDomain);
        businessRulesDomain.BusinessvalidationDomainRules(businessDomain);
        BusinessEntity businessEntity = businessMapperEntity.toEntity(businessDomain);
        businessEntity.setId(UUID.randomUUID());
        businessRepository.save(businessEntity);
    }

}
