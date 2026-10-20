package services;

import models.Project;
import models.StatusReport;
import utils.exceptions.EmptyProjectException;

public class ReportService {

    private final ProjectService projectService;

    /** Uses the project service to get the projects. */
    public ReportService(ProjectService projectService) {
        this.projectService = projectService;
    }

    /** Returns one report per project. */
    public StatusReport[] generateReports() {
        Project[] projects = projectService.getProjects();
        StatusReport[] reports = new StatusReport[projects.length];

        for (int i = 0; i < projects.length; i++) {
            reports[i] = new StatusReport(projects[i]);
        }
        return reports;
    }

    /** Returns the average completion of the projects that have tasks, throws EmptyProjectException if none has tasks. */
    public double calculateAverageCompletion(StatusReport[] reports) {
        double total = 0;
        int counted = 0;

        // projects without tasks are skipped, otherwise they would count as 0%
        for (StatusReport report : reports) {
            if (report.hasTasks()) {
                total += report.getPercentage();
                counted++;
            }
        }

        if (counted == 0) {
            throw new EmptyProjectException("No project has tasks yet, so the average cannot be calculated.");
        }
        return Math.round(total / counted * 100.0) / 100.0;
    }
}
