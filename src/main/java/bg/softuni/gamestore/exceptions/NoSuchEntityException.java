package bg.softuni.gamestore.exceptions;

public class NoSuchEntityException extends RuntimeException {
    public NoSuchEntityException(String reason) {
        super(reason);
    }
}
