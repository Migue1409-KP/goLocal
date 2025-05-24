package co.uco.golocal.golocalapi.domain.usuario.reglasdomain;

import co.uco.golocal.golocalapi.domain.usuario.exception.DuplicateNumeroIdException;
import co.uco.golocal.golocalapi.repository.usuario.IUsuarioRepositorio;
import org.springframework.stereotype.Service;

@Service
public class UnicoTipoNumeroIdRegla {
    private final IUsuarioRepositorio iUsuarioRepositorio;


    public UnicoTipoNumeroIdRegla(IUsuarioRepositorio iUsuarioRepositorio) {
        this.iUsuarioRepositorio = iUsuarioRepositorio;
    }

    public void validacionReglaNumeroId(String identificacion){
        if(iUsuarioRepositorio.existsByIdentificacion(identificacion)){
            throw new DuplicateNumeroIdException("El numero de identificacion "+ identificacion + " ya esta registrado.");
        }
    }
}
