package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.security.CurrentUser;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageTaskService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageUserService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.html.H1;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route("tasks/create")
public class CreateTaskView extends HorizontalLayout {

    private ManageProjectService manageProjectService;
    private ManageTaskService manageTaskService;
    private CurrentUser currentUser;
    private ManageUserService manageUserService;

    @Autowired
    public CreateTaskView(CurrentUser currentUser,
                          ManageTaskService manageTaskService,
                          ManageProjectService manageProjectService,
                          ManageUserService manageUserService
    ) {
        this.manageTaskService = manageTaskService;
        this.manageProjectService = manageProjectService;
        this.currentUser = currentUser;

        FormLayout formLayout = new FormLayout();

        // Form fields
        TextField taskName = new TextField("Task Name");
        DateTimePicker startTime = new DateTimePicker("Start Time");
        DateTimePicker endTime = new DateTimePicker("End Time");

        ComboBox<Project> projectComboBox = new ComboBox<>("Project");

        Pageable pageable = PageRequest.of(0, 10);
        projectComboBox.setItems(manageProjectService.getAllProjects(pageable).getContent());
        projectComboBox.setItemLabelGenerator(Project::getName);

        ComboBox<TaskOld> dependencyComboBox = new ComboBox<>("Dependency");
        // TODO: Carregar tasks do seu service ou repository
         dependencyComboBox.setItems(manageTaskService.getAllTasks(pageable).getContent());
        dependencyComboBox.setItemLabelGenerator(TaskOld::getName);

        // Binder
        Binder<TaskDto> binder = new Binder<>(TaskDto.class);
        TaskDto taskDto = new TaskDto();

        binder.forField(taskName)
                .asRequired("Task name is required")
                .bind(TaskDto::getName, TaskDto::setName);

        binder.forField(startTime)
                .bind(TaskDto::getStartTime, TaskDto::setStartTime);

        binder.forField(endTime)
                .bind(TaskDto::getEndTime, TaskDto::setEndTime);


        var id = currentUser.getWorker(manageUserService).getId();
        var idList = new ArrayList<Long>();
        idList.add(id);

        taskDto.setWorkerIds(idList);
        binder.readBean(taskDto);

        // Create button
        Button createButton = new Button("Create Task");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.setDisableOnClick(true);

        createButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                if (binder.writeBeanIfValid(taskDto)) {
                    try {
                        // Set optional project and dependency IDs
                        Project selectedProject = projectComboBox.getValue();
                        if (selectedProject != null) {
                            taskDto.setProjectId(selectedProject.getId());
                        }

                        TaskOld selectedDependency = dependencyComboBox.getValue();
                        if (selectedDependency != null) {
                            taskDto.setDependencyId(selectedDependency.getId());
                        }

                        // TODO: Set current worker IDs as necessário
                        taskDto.setWorkerIds(List.of(2L));

                        manageTaskService.saveTask(taskDto);
                        Notification.show("Task created successfully!", 3000, Notification.Position.TOP_CENTER);
                        clearForm(binder);
                        createButton.setEnabled(true);
                    } catch (Exception e) {
                        createButton.setEnabled(true);
                        Notification.show("Error creating task: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
                    }
                }
            } else {
                createButton.setEnabled(true);
            }
        });

        // Add to layout
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        formLayout.add(
                taskName, startTime, endTime,
                projectComboBox, dependencyComboBox,
                createButton
        );

        add(formLayout);
    }

    private void clearForm(Binder<TaskDto> binder) {
        TaskDto taskDto = new TaskDto();
        binder.readBean(taskDto);
    }
}
