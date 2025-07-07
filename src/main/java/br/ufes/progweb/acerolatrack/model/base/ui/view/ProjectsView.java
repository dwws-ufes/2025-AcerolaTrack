package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.security.AppUserInfo;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.Worker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;

@PermitAll
@Route("projects")
public class ProjectsView extends HorizontalLayout {

    ManageProjectService manageProjectService;

    HorizontalLayout layout = new HorizontalLayout();

    @Autowired
    public ProjectsView(ManageProjectService manageProjectService){
        TextField projectName = new TextField("Project Name");
        FormLayout formLayout = new FormLayout();
        TextArea description = new TextArea();
        this.manageProjectService = manageProjectService;

        Binder<Project> binder = new Binder<>(Project.class);
        Project project = new Project();

        // Start by defining the Field instance to use
        binder.forField(projectName)
                // Finalize by doing the actual binding
                // to the Person class
                .bind(
                        // Callback that loads the title
                        // from a person instance
                        Project::getName,
                        // Callback that saves the title
                        // in a person instance
                        Project::setName);

        binder.forField(description)
                // Finalize by doing the actual binding
                // to the Person class
                .bind(
                        // Callback that loads the title
                        // from a person instance
                        Project::getDescription,
                        // Callback that saves the title
                        // in a person instance
                        Project::setDescription);

        description.setWidthFull();
        description.setLabel("Description");
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.addValueChangeListener(e -> {
            e.getSource()
                    .setHelperText(e.getValue().length() + "/" + 300);
        });

        binder.readBean(project);

        Button saveButton = new Button("Save",
                event -> {
                    try {
                        binder.writeBean(project);
                        // A real application would also save
                        // the updated person
                        // using the application's backend
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                });

        Button button = new Button("Perform Action");
        button.setDisableOnClick(true);

        //TODO Usar getCurrentUser para setar o Worker
        Worker worker = new Worker();
        worker.setId(2L);
        worker.setUsername("AASAS");

        ProjectDto projectDto = getProjectDto(worker, project);
        button.addClickListener(event -> manageProjectService.createProject(projectDto));

        formLayout.setAutoResponsive(true);
        formLayout.add(projectName, description, button);
        add(formLayout);
    }

    private ProjectDto getProjectDto(Worker worker, Project project) {
        project.setName(project.getName());

        ProjectDto projectDto = new ProjectDto();
        projectDto.setName(project.getName());
        projectDto.setDescription(project.getDescription());
        projectDto.setStatus(project.getStatus());
        projectDto.setWorkerIds(new ArrayList<Long>(){{add(worker.getId());}});

        return projectDto;
    }
}
