package bg.softuni.gamestore.exceptions;

public class ValidationException extends RuntimeException {
    public ValidationException(String reason) {
        super(reason);
    }
}
