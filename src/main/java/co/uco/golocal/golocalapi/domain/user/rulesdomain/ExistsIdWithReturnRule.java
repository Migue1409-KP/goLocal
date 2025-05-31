package co.uco.golocal.golocalapi.domain.user.rulesdomain;

import co.uco.golocal.golocalapi.data.entity.user.UserEntity;
import co.uco.golocal.golocalapi.domain.user.exception.DoesntExistIdException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ExistsIdWithReturnRule {
    private final IUserRepository iUserRepository;


    public ExistsIdWithReturnRule(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public UserEntity execute(UUID id){
        return iUserRepository.findById(id)
                .orElseThrow(() -> new DoesntExistIdException("User id: " + id + " does not exist"));
    }
}
