package nl.whitehorses.hellobeer.service.errors;

public class InvalidOrderException extends Exception {

    public InvalidOrderException(String defaultMessage) {
        super(defaultMessage);
    }

}
