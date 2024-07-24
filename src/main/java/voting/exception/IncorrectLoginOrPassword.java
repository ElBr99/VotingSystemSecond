package voting.exception;

public class IncorrectLoginOrPassword extends RuntimeException {

    public IncorrectLoginOrPassword(String message) {
        super(message);
    }
}
