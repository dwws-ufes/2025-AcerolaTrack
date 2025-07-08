package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.ProjectReport;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route("project-reports")
public class DashboardView extends VerticalLayout {

    private final ManageProjectService manageProjectService;

    @Autowired
    public DashboardView(ManageProjectService manageProjectService) {
        this.manageProjectService = manageProjectService;

        setSizeFull();
        Grid<ProjectReport> grid = new Grid<>(ProjectReport.class, false);

        grid.addColumn(ProjectReport::getProjectName).setHeader("Project Name").setAutoWidth(true);
        grid.addColumn(ProjectReport::getTotal).setHeader("Spent Time (minutes)").setAutoWidth(true);

        Pageable pageable = PageRequest.of(0, 10);
        List<Project> projectList = manageProjectService.getAllProjects(pageable).getContent();

        List<ProjectReport> reports = new ArrayList<ProjectReport>();

        for (Project project : projectList) {
            reports.add(manageProjectService.getProjectReport(project.getId()));
        }

        grid.setItems(reports);
        add(grid);
    }
}
