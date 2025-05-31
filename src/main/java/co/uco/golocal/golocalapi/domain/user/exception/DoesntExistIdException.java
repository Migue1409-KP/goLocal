package co.uco.golocal.golocalapi.domain.user.exception;

public class DoesntExistIdException extends RuntimeException{
    public DoesntExistIdException(String message) {
        super(message);
    }
}
