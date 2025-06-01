package co.uco.golocal.golocalapi.domain.business.businessrulesdomain;

import co.uco.golocal.golocalapi.domain.business.exception.BusinessNameAlreadyExistsException;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicNameBusiness {
    private final IBusinessRepository iBusinessRepository;

    public UnicNameBusiness(IBusinessRepository iBbusinessRepository) {
        this.iBusinessRepository = iBbusinessRepository;
    }

    public void execute(String name) {
        if (iBusinessRepository.existsByName(name)) {
            throw new BusinessNameAlreadyExistsException(name);
        }
    }

}
