package co.uco.golocal.golocalapi.domain.user.exception;

public class DuplicatePhoneException extends RuntimeException{
    public DuplicatePhoneException(String message) {
        super(message);
    }
}
