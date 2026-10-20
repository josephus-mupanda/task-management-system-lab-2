package utils.exceptions;

// thrown when no task has the id the user typed
public class TaskNotFoundException extends RuntimeException {

    /** Creates the exception for the id the user typed. */
    public TaskNotFoundException(String taskId) {
        super("Task '" + taskId + "' was not found in the project.");
    }
}
