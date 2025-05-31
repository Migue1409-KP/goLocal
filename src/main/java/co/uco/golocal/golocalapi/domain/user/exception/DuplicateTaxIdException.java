package co.uco.golocal.golocalapi.domain.user.exception;

public class DuplicateTaxIdException extends RuntimeException{
    public DuplicateTaxIdException(String message) {
        super(message);
    }
}
