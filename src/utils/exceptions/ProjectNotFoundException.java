package utils.exceptions;

// thrown when no project has the id the user typed
public class ProjectNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** Creates the exception for the id the user typed. */
    public ProjectNotFoundException(String projectId) {
        super("Project ID '" + projectId + "' does not exist.");
    }
}
