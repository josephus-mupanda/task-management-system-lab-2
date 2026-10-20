package models;

import utils.exceptions.EmptyProjectException;
import utils.exceptions.InvalidProjectDataException;
import utils.exceptions.TaskNotFoundException;

import java.util.Arrays;

// what every project has, SoftwareProject and HardwareProject add their own detail
public abstract class Project {

    private final String id;
    private final String name;
    private final String description;
    private final double budget;
    private final int teamSize;
    private final int durationWeeks;
    private Task[] tasks = new Task[10];
    private int taskCount;

    /** Creates a project, throws InvalidProjectDataException if a value is not valid. */
    public Project(String id, String name, String description, double budget, int teamSize, int durationWeeks) {
        if (id == null || id.trim().isEmpty()) {
            throw new InvalidProjectDataException("Project ID cannot be empty.");
        }
        if (name == null || name.trim().isEmpty()) {
            throw new InvalidProjectDataException("Project name cannot be empty.");
        }
        if (budget <= 0) {
            throw new InvalidProjectDataException("Budget must be greater than 0.");
        }
        if (teamSize <= 0) {
            throw new InvalidProjectDataException("Team size must be greater than 0.");
        }
        if (durationWeeks <= 0) {
            throw new InvalidProjectDataException("Duration must be greater than 0.");
        }
        this.id = id.trim();
        this.name = name.trim();
        this.description = description == null ? "" : description.trim();
        this.budget = budget;
        this.teamSize = teamSize;
        this.durationWeeks = durationWeeks;
    }

    /** Returns the kind of project, ex: Software. */
    public abstract String getType();

    /** Returns the line that depends on the type, ex: Tech Stack: Java. */
    public abstract String getProjectDetails();

    /** Adds a task, the array doubles in size when it is full. */
    public void addTask(Task task) {
        if (taskCount == tasks.length) {
            tasks = Arrays.copyOf(tasks, tasks.length * 2);
        }
        tasks[taskCount++] = task;
    }

    /** Returns the task with this id, throws TaskNotFoundException if it is not in this project. */
    public Task findTask(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equals(taskId)) {
                return tasks[i];
            }
        }
        throw new TaskNotFoundException(taskId);
    }

    /** Returns true if the task is in this project. */
    public boolean hasTask(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equals(taskId)) {
                return true;
            }
        }
        return false;
    }

    /** Removes the task and moves the next tasks one place to the left, throws TaskNotFoundException if it is not here. */
    public void removeTask(String taskId) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getId().equals(taskId)) {
                for (int j = i; j < taskCount - 1; j++) {
                    tasks[j] = tasks[j + 1];
                }
                tasks[--taskCount] = null;
                return;
            }
        }
        throw new TaskNotFoundException(taskId);
    }

    /** Returns true if a task in this project already has this name (ignoring case). */
    public boolean hasTaskNamed(String taskName) {
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getName().equalsIgnoreCase(taskName.trim())) {
                return true;
            }
        }
        return false;
    }

    /** Returns a copy of the tasks so the array inside the project cannot be changed from outside. */
    public Task[] getTasks() {
        return Arrays.copyOf(tasks, taskCount);
    }

    /** Returns the number of tasks. */
    public int getTaskCount() {
        return taskCount;
    }

    /** Returns true if the project has at least one task. */
    public boolean hasTasks() {
        return taskCount > 0;
    }

    /** Counts the tasks that have this status. */
    public int countByStatus(Task.Status status) {
        int count = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].getStatus() == status) {
                count++;
            }
        }
        return count;
    }

    /** Returns the % of completed tasks rounded to 2 decimals, throws EmptyProjectException when there are no tasks. */
    public double calculateCompletionPercentage() {
        if (taskCount == 0) {
            throw new EmptyProjectException(
                    "Project '" + name + "' has no tasks, so its completion cannot be calculated.");
        }
        int completed = 0;
        for (int i = 0; i < taskCount; i++) {
            if (tasks[i].isCompleted()) {
                completed++;
            }
        }
        double percentage = (double) completed / taskCount * 100;
        return Math.round(percentage * 100.0) / 100.0;
    }

    /** Returns the id, ex: PRJ001. */
    public String getId() {
        return id;
    }

    /** Returns the name. */
    public String getName() {
        return name;
    }

    /** Returns the description. */
    public String getDescription() {
        return description;
    }

    /** Returns the budget. */
    public double getBudget() {
        return budget;
    }

    /** Returns the team size. */
    public int getTeamSize() {
        return teamSize;
    }

    /** Returns the duration in weeks. */
    public int getDurationWeeks() {
        return durationWeeks;
    }
}
