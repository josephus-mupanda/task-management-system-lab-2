package services;

import models.Project;
import models.Task;
import models.User;
import utils.ValidationUtils;
import utils.exceptions.InvalidInputException;
import utils.exceptions.TaskNotFoundException;

// task operations, task ids (TSK001...) are unique across all projects
public class TaskService {

    private final ProjectService projectService;
    private int nextNumber = 1;

    /** Uses the project service to look for tasks in every project. */
    public TaskService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /** Creates a task with the next id and adds it to the project, throws InvalidInputException if the name is already used there. */
    public Task addTask(Project project, String name, Task.Status status, int hours, User assignee) {
        if (project.hasTaskNamed(name)) {
            throw new InvalidInputException("A task named '" + name.trim() + "' already exists in this project.");
        }
        Task task = new Task(String.format("TSK%03d", nextNumber), name, status, hours);
        task.setAssignedTo(assignee);
        project.addTask(task);
        nextNumber++;
        return task;
    }

    /** Returns the project that has this task, throws TaskNotFoundException if no project has it. */
    public Project findProjectOfTask(String input) {
        String taskId = ValidationUtils.normalizeId(input, "TSK");
        for (Project project : projectService.getProjects()) {
            if (project.hasTask(taskId)) {
                return project;
            }
        }
        throw new TaskNotFoundException(input.trim());
    }

    /** Finds a task inside one project, throws TaskNotFoundException if it is not there. */
    public Task findTask(Project project, String input) {
        return project.findTask(ValidationUtils.normalizeId(input, "TSK"));
    }

    /** Changes the status of a task, throws TaskNotFoundException if it is not in the project. */
    public Task updateTaskStatus(Project project, String input, Task.Status status) {
        Task task = findTask(project, input);
        task.setStatus(status);
        return task;
    }

    /** Removes a task from the project, throws TaskNotFoundException if it is not there. */
    public Task removeTask(Project project, String input) {
        Task task = findTask(project, input);
        project.removeTask(task.getId());
        return task;
    }
}
