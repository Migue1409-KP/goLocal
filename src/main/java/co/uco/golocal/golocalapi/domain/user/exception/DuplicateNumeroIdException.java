package co.uco.golocal.golocalapi.domain.user.exception;

public class DuplicateNumeroIdException extends RuntimeException{
    public DuplicateNumeroIdException(String message) {
        super(message);
    }
}
