package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.model.Status;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.component.textfield.NumberField;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.data.value.ValueChangeMode;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;

@PermitAll
@Route("projects/create")
public class CreateProjectView extends HorizontalLayout {

    ManageProjectService manageProjectService;

    @Autowired
    public CreateProjectView(ManageProjectService manageProjectService) {
        this.manageProjectService = manageProjectService;

        FormLayout formLayout = new FormLayout();

        // Create form fields
        TextField projectName = new TextField("Project Name");
        TextArea description = new TextArea("Description");
        DateTimePicker startTime = new DateTimePicker("Start Time");
        DateTimePicker endTime = new DateTimePicker("End Time");
        DateTimePicker dueDate = new DateTimePicker("Due Date");
        ComboBox<Status> status = new ComboBox<>("Status");
        status.setItems(Status.values());
        NumberField customerId = new NumberField("Customer ID");

        // Configure description field
        description.setWidthFull();
        description.setValueChangeMode(ValueChangeMode.EAGER);
        description.addValueChangeListener(e -> e.getSource().setHelperText(e.getValue().length() + "/" + 300));

        // Create binder
        Binder<ProjectDto> binder = new Binder<>(ProjectDto.class);
        ProjectDto projectDto = new ProjectDto();

        // Bind fields
        binder.forField(projectName)
                .asRequired("Project name is required")
                .bind(ProjectDto::getName, ProjectDto::setName);

        binder.forField(description)
                .bind(ProjectDto::getDescription, ProjectDto::setDescription);

        binder.forField(startTime)
                .bind(ProjectDto::getStartTime, ProjectDto::setStartTime);

        binder.forField(endTime)
                .bind(ProjectDto::getEndTime, ProjectDto::setEndTime);

        binder.forField(dueDate)
                .bind(ProjectDto::getDueDate, ProjectDto::setDueDate);

        binder.forField(status)
                .asRequired("Status is required")
                .bind(ProjectDto::getStatus, ProjectDto::setStatus);

        binder.forField(customerId)
                .withConverter(
                    number -> number == null ? null : number.longValue(),
                    value -> value == null ? null : value.doubleValue()
                )
                .bind(ProjectDto::getCustomerId, ProjectDto::setCustomerId);

        binder.readBean(projectDto);

        Button createButton = new Button("Create Project");
        createButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        createButton.setDisableOnClick(true);

        createButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                ProjectDto newProject = new ProjectDto();
                if (binder.writeBeanIfValid(newProject)) {
                    // Set current worker
                    List<Long> workerIds = new ArrayList<>();
                    workerIds.add(2L); // TODO: Replace with actual current user ID
                    newProject.setWorkerIds(workerIds);

                    try {
                        manageProjectService.createProject(newProject);
                        createButton.setEnabled(true);
                        Notification.show("Project created successfully!", 3000, Notification.Position.TOP_CENTER);
                        clearForm(binder);
                    } catch (Exception e) {
                        createButton.setEnabled(true);
                        Notification.show("Error creating project: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
                    }
                }
            } else {
                createButton.setEnabled(true);
            }
        });

        // Add components to form
        formLayout.setResponsiveSteps(
                new FormLayout.ResponsiveStep("0", 1),
                new FormLayout.ResponsiveStep("500px", 2)
        );

        formLayout.add(
                projectName, description,
                startTime, endTime,
                dueDate, status,
                customerId, createButton
        );

        add(formLayout);
    }

    private void clearForm(Binder<ProjectDto> binder) {
        ProjectDto projectDto = new ProjectDto();
        binder.readBean(projectDto);
    }
}
