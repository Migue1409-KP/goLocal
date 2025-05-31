package co.uco.golocal.golocalapi.domain.user.rulesdomain;

import co.uco.golocal.golocalapi.domain.user.exception.DuplicateEmailException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UniqueEmailRule {
    private final IUserRepository iUserRepository;


    public UniqueEmailRule(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void execute(String email){
        if(iUserRepository.existsByEmail(email)){
            throw new DuplicateEmailException("User email: "+ email+ " is already registered.");
        }
    }
}
