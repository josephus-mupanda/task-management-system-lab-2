package utils.exceptions;

// thrown when a value typed by the user is not valid (empty text, letters instead of a number...)
public class InvalidInputException extends RuntimeException {

    /** Creates the exception with the message shown to the user. */
    public InvalidInputException(String message) {
        super(message);
    }
}
