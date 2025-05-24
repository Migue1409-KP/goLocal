package co.uco.golocal.golocalapi.domain.usuario.reglasdomain;

import co.uco.golocal.golocalapi.domain.usuario.exception.DuplicateNumeroIdException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicoTipoNumeroIdRegla {
    private final IUserRepository iUserRepository;


    public UnicoTipoNumeroIdRegla(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void validacionReglaNumeroId(String identificacion){
        if(iUserRepository.existsByTaxId(identificacion)){
            throw new DuplicateNumeroIdException("El numero de identificacion "+ identificacion + " ya esta registrado.");
        }
    }
}
