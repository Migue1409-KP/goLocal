package co.uco.golocal.golocalapi.domain.user.rulesdomain;

import co.uco.golocal.golocalapi.domain.user.exception.DoesntExistIdException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistsIdRule {
    private final IUserRepository iUserRepository;


    public ExistsIdRule(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void execute(UUID id){
        if(!iUserRepository.existsById(id)){
            throw new DoesntExistIdException("User id: "+ id + " does not exist");
        }
    }
}
