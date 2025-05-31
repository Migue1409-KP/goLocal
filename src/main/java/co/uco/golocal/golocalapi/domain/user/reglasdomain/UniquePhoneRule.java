package co.uco.golocal.golocalapi.domain.user.reglasdomain;
import co.uco.golocal.golocalapi.domain.user.exception.DuplicatePhoneException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UniquePhoneRule {
    private final IUserRepository iUserRepository;


    public UniquePhoneRule(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void execute(String phone){
        if(iUserRepository.existsByPhone(phone)){
            throw new DuplicatePhoneException("User phone: "+ phone+ " already exists");
        }
    }
}
