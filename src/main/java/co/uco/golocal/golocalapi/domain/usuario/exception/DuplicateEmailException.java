package co.uco.golocal.golocalapi.domain.usuario.exception;

public class DuplicateEmailException extends RuntimeException{
    public DuplicateEmailException(String message) {
        super(message);
    }
}
