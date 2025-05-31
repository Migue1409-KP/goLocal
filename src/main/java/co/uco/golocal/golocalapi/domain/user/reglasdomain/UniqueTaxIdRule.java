package co.uco.golocal.golocalapi.domain.user.reglasdomain;

import co.uco.golocal.golocalapi.domain.user.exception.DuplicateTaxIdException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UniqueTaxIdRule {
    private final IUserRepository iUserRepository;


    public UniqueTaxIdRule(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void execute(String taxId){
        if(iUserRepository.existsByTaxId(taxId)){
            throw new DuplicateTaxIdException("User  taxId: "+ taxId + " already exists");
        }
    }
}
