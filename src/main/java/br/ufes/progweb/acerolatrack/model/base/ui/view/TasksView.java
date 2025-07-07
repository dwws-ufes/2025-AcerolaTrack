package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageTaskService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.Task;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import br.ufes.progweb.acerolatrack.model.Worker;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route("tasks")
public class TasksView extends VerticalLayout {

    ComboBox<TaskOld> comboBox;

    ManageTaskService manageTaskService;

    ManageProjectService manageProjectService;

    @Autowired
    public TasksView(ManageTaskService manageTaskService, ManageProjectService manageProjectService) {
        this.manageTaskService = manageTaskService;
        this.manageProjectService = manageProjectService;

        comboBox = new ComboBox<>();
        comboBox.setItemLabelGenerator(TaskOld::getName);
        addClassName(LumoUtility.Padding.MEDIUM);
        add(getTaskGrid());
    }

    public Component getTaskGrid() {
        Project project = new Project();
        project.setId(3L); // caso precise setar o id manualmente
        project.setName("NASA");
        Worker worker = new Worker();
        worker.setId(2L);
        worker.setUsername("AASAS");

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setStatus(project.getStatus());
        projectDto.setWorkerIds(new ArrayList<Long>(){{add(worker.getId());}});

        manageProjectService.createProject(projectDto);

        // Sets items as a collection
        Pageable pageable = PageRequest.of(0, 10);
        Page pageable2 = manageTaskService.getAllTasks(pageable);
        comboBox.setItems(pageable2.getContent());

        // Show such beans in a Grid
        Grid<TaskOld> grid = new Grid<>(TaskOld.class, false);

        grid.addColumn(TaskOld::getName)
                .setHeader("Name");
        grid.addColumn(task -> task.getProject() == null ? "" : task.getProject().getName())
                .setHeader("Project");

        grid.setItems(pageable2.getContent());

        return grid;
    }
}
