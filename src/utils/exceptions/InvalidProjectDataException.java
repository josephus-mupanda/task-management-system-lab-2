package utils.exceptions;

// thrown when project data is not valid: negative budget, empty name, id or name already used
public class InvalidProjectDataException extends InvalidInputException {

    private static final long serialVersionUID = 1L;

    /** Creates the exception with the message shown to the user. */
    public InvalidProjectDataException(String message) {
        super(message);
    }
}
