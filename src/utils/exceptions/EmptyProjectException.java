package utils.exceptions;

// thrown when we try to calculate the completion of a project that has no tasks
public class EmptyProjectException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Creates the exception with the message shown to the user. */
    public EmptyProjectException(String message) {
        super(message);
    }
}
