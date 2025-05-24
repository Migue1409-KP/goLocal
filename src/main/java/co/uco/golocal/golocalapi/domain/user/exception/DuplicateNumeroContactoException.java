package co.uco.golocal.golocalapi.domain.user.exception;

public class DuplicateNumeroContactoException extends RuntimeException{
    public DuplicateNumeroContactoException(String message) {
        super(message);
    }
}
