package co.uco.golocal.golocalapi.domain.usuario.reglasdomain;

import co.uco.golocal.golocalapi.domain.usuario.exception.DuplicateEmailException;
import co.uco.golocal.golocalapi.repository.usuario.IUserRepository;
import org.springframework.stereotype.Service;

@Service
public class UnicoEmailRegla {
    private final IUserRepository iUserRepository;


    public UnicoEmailRegla(IUserRepository iUserRepository) {
        this.iUserRepository = iUserRepository;
    }

    public void validacionReglaEmail(String correo){
        if(iUserRepository.existsByEmail(correo)){
            throw new DuplicateEmailException("El correo "+ correo+ " ya esta registrado.");
        }
    }
}
