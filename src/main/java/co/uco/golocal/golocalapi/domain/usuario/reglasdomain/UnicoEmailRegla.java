package co.uco.golocal.golocalapi.domain.usuario.reglasdomain;

import co.uco.golocal.golocalapi.domain.usuario.exception.DuplicateEmailException;
import co.uco.golocal.golocalapi.repository.usuario.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class UnicoEmailRegla {
    private final IUsuarioRepositorio iUsuarioRepositorio;


    public UnicoEmailRegla(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    public void validacionReglaEmail(String correo){
        if(iUsuarioRepositorio.existsByCorreo(correo)){
            throw new DuplicateEmailException("El correo "+ correo+ " ya esta registrado.");
        }
    }
}
