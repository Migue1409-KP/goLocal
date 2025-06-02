package co.uco.golocal.golocalapi.domain.business.businessrulesdomain;

import co.uco.golocal.golocalapi.domain.business.exception.DoesntBussinesExistIdException;
import co.uco.golocal.golocalapi.repository.business.IBusinessRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExisBussinestIdRule {
    private final IBusinessRepository iBusinessRepository;

    public ExisBussinestIdRule(IBusinessRepository iBusinessRepository) {
        this.iBusinessRepository = iBusinessRepository;
    }
    public void execute(UUID id) {
        if (!iBusinessRepository.existsById(id)) {
            throw new DoesntBussinesExistIdException("Business ID: " + id+ " does not exist.");
        }
    }


}
