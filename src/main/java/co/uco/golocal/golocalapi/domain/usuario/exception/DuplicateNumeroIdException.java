package co.uco.golocal.golocalapi.domain.usuario.exception;

public class DuplicateNumeroIdException extends RuntimeException{
    public DuplicateNumeroIdException(String message) {
        super(message);
    }
}
