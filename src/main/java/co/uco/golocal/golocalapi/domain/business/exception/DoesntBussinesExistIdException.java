package co.uco.golocal.golocalapi.domain.business.exception;

public class DoesntBussinesExistIdException extends RuntimeException {
    public DoesntBussinesExistIdException(String message) {
        super(message);
    }
}
