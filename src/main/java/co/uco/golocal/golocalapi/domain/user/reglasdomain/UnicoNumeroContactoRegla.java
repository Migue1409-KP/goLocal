package co.uco.golocal.golocalapi.domain.user.reglasdomain;
import co.uco.golocal.golocalapi.domain.user.exception.DuplicateNumeroContactoException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicoNumeroContactoRegla {
    private final IUserRepository iUserRepository;


    public UnicoNumeroContactoRegla(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void validacionReglaNumeroContacto(String numeroContacto){
        if(iUserRepository.existsByPhone(numeroContacto)){
            throw new DuplicateNumeroContactoException("Ya fue registrado este numero: "+ numeroContacto+ " anteriormente.");
        }
    }
}
