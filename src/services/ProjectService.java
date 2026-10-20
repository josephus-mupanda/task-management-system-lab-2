package services;

import models.HardwareProject;
import models.Project;
import models.SoftwareProject;
import utils.ValidationUtils;
import utils.exceptions.InvalidProjectDataException;
import utils.exceptions.ProjectNotFoundException;

import java.util.Arrays;

// keeps the projects in an array, it never prints or reads the keyboard
public class ProjectService {

    private Project[] projects = new Project[10];
    private int count;
    private int nextNumber = 1;

    /** Creates a software project with the next id (PRJ001, PRJ002...) and saves it. */
    public Project createSoftwareProject(String name, String description, double budget,
                                         int teamSize, int durationWeeks, String techStack) {
        Project project = new SoftwareProject(nextId(), name, description, budget, teamSize, durationWeeks, techStack);
        addProject(project);
        nextNumber++;
        return project;
    }

    /** Creates a hardware project with the next id (PRJ001, PRJ002...) and saves it. */
    public Project createHardwareProject(String name, String description, double budget,
                                         int teamSize, int durationWeeks, String deviceType) {
        Project project = new HardwareProject(nextId(), name, description, budget, teamSize, durationWeeks, deviceType);
        addProject(project);
        nextNumber++;
        return project;
    }

    /** Saves a project, throws InvalidProjectDataException if its id or its name is already used. */
    public void addProject(Project project) {
        if (findById(project.getId()) != null) {
            throw new InvalidProjectDataException("Project ID '" + project.getId() + "' already exists.");
        }
        if (nameExists(project.getName())) {
            throw new InvalidProjectDataException("A project named '" + project.getName() + "' already exists.");
        }
        if (count == projects.length) {
            projects = Arrays.copyOf(projects, projects.length * 2);
        }
        projects[count++] = project;
    }

    /** Returns true if a project already has this name (ignoring case). */
    public boolean nameExists(String name) {
        for (int i = 0; i < count; i++) {
            if (projects[i].getName().equalsIgnoreCase(name.trim())) {
                return true;
            }
        }
        return false;
    }

    /** Finds a project from what the user typed ("PRJ001" or "1"), throws ProjectNotFoundException if there is none. */
    public Project findProject(String input) {
        Project project = findById(ValidationUtils.normalizeId(input, "PRJ"));
        if (project == null) {
            throw new ProjectNotFoundException(input.trim());
        }
        return project;
    }

    /** Returns all the projects. */
    public Project[] getProjects() {
        return Arrays.copyOf(projects, count);
    }

    /** Returns the projects of one type, "Software" or "Hardware". */
    public Project[] getByType(String type) {
        Project[] result = new Project[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            if (projects[i].getType().equalsIgnoreCase(type)) {
                result[found++] = projects[i];
            }
        }
        return Arrays.copyOf(result, found);
    }

    /** Returns the projects with a budget between min and max. */
    public Project[] getByBudgetRange(double min, double max) {
        Project[] result = new Project[count];
        int found = 0;
        for (int i = 0; i < count; i++) {
            double budget = projects[i].getBudget();
            if (budget >= min && budget <= max) {
                result[found++] = projects[i];
            }
        }
        return Arrays.copyOf(result, found);
    }

    private Project findById(String id) {
        for (int i = 0; i < count; i++) {
            if (projects[i].getId().equals(id)) {
                return projects[i];
            }
        }
        return null;
    }

    private String nextId() {
        return String.format("PRJ%03d", nextNumber);
    }
}
