package nl.whitehorses.hellobeer.web.rest.errors;

public class InvalidOrderException extends BadRequestAlertException {

    public InvalidOrderException(String defaultMessage, String entityName, String errorKey) {
        super(defaultMessage, entityName, errorKey);
    }

}
