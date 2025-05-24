package co.uco.golocal.golocalapi.domain.usuario.exception;

public class DuplicateNumeroContactoException extends RuntimeException{
    public DuplicateNumeroContactoException(String message) {
        super(message);
    }
}
