package co.uco.golocal.golocalapi.domain.business.businessrulesdomain;

import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicNameBusiness {
    private final IBusinessRepository iBbusinessRepository;

    public UnicNameBusiness(IBusinessRepository iBbusinessRepository) {
        this.iBbusinessRepository = iBbusinessRepository;
    }

    public void  validationRuleName(String name) {
        if (iBbusinessRepository.existsByName(name)) {
            throw new RuntimeException("El nombre de negocio" +name+" ya existe ");
        }
    }

}
