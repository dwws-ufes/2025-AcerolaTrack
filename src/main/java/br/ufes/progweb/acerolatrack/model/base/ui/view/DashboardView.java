package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageTaskService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import com.vaadin.flow.component.grid.GridVariant;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.treegrid.TreeGrid;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Duration;
import java.util.List;

    @PermitAll
    @Route("projects-board")
    public class DashboardView extends VerticalLayout {

        private final ManageProjectService manageProjectService;
        private final ManageTaskService manageTaskService;

        @Autowired
        public DashboardView(ManageProjectService manageProjectService, ManageTaskService manageTaskService) {
            this.manageProjectService = manageProjectService;
            this.manageTaskService = manageTaskService;

            TreeGrid<Object> treeGrid = new TreeGrid<>();

            // Coluna do nome (Project ou Task)
            treeGrid.addHierarchyColumn(item -> {
                if (item instanceof Project project) {
                    return "📁 " + project.getName();
                } else if (item instanceof TaskOld task) {
                    return "📝 " + task.getName();
                } else {
                    return "";
                }
            }).setHeader("Name");

            // Coluna de horas calculadas
            treeGrid.addColumn(item -> {
                if (item instanceof Project project) {
                    List<TaskOld> tasks = manageTaskService.getTasksByProject(project);
                    double totalHours = tasks.stream()
                            .filter(t -> t.getStartTime() != null && t.getEndTime() != null)
                            .mapToDouble(t -> Duration.between(t.getStartTime(), t.getEndTime()).toHours())
                            .sum();
                    return totalHours + "h";
                } else if (item instanceof TaskOld task) {
                    if (task.getStartTime() != null && task.getEndTime() != null) {
                        long hours = Duration.between(task.getStartTime(), task.getEndTime()).toHours();
                        return hours + "h";
                    } else {
                        return "-";
                    }
                } else {
                    return "";
                }
            }).setHeader("Total Hours");

            treeGrid.addThemeVariants(GridVariant.LUMO_ROW_STRIPES);

            // Carregar dados
            List<Project> projects = manageProjectService.getAllProjects();
            treeGrid.setItems(projects, project -> manageProjectService.getTasks());

            add(treeGrid);
        }
    }

}
