package bg.softuni.gamestore.exceptions;

public class LoggedUserException extends RuntimeException {
    public LoggedUserException(String reason) {
        super(reason);
    }
}
