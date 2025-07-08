package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.core.security.CurrentUser;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageTaskService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageTimeEntryService;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageUserService;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import br.ufes.progweb.acerolatrack.model.Worker;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.combobox.ComboBox;
import com.vaadin.flow.component.datetimepicker.DateTimePicker;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;
import jakarta.annotation.security.PermitAll;
import org.hibernate.query.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.util.List;

@PermitAll
@Route("time-entries/create")
public class CreateTimeEntryView extends VerticalLayout {

    private final ManageTimeEntryService manageTimeEntryService;
    private final ManageTaskService manageTaskService;
    private final CurrentUser currentUser;
    private final ManageUserService manageUserService;

    @Autowired
    public CreateTimeEntryView(
            CurrentUser currentUser,
            ManageTimeEntryService manageTimeEntryService,
            ManageTaskService manageTaskService,
            ManageUserService manageUserService
    ) {
        this.manageTimeEntryService = manageTimeEntryService;
        this.manageTaskService = manageTaskService;
        this.currentUser = currentUser;
        this.manageUserService = manageUserService;

        FormLayout formLayout = new FormLayout();

        // Form fields
        TextArea description = new TextArea("Description");
        description.setWidthFull();

        DateTimePicker startTime = new DateTimePicker("Start Time");
        DateTimePicker endTime = new DateTimePicker("End Time");

        TextField tag = new TextField("Tag");

//        ComboBox<Worker> workerComboBox = new ComboBox<>("Worker");
//        List<Worker> workers = manageWorkerService.getAllWorkers(); // ajuste conforme seu serviço real
//        workerComboBox.setItems(workers);
//        workerComboBox.setItemLabelGenerator(Worker::getUsername);

        ComboBox<TaskOld> taskComboBox = new ComboBox<>("Task");
        Pageable pageable = PageRequest.of(0, 10);
        List<TaskOld> tasks = manageTaskService.getAllTasks(pageable).getContent(); // ajuste conforme seu serviço real
        taskComboBox.setItems(tasks);
        taskComboBox.setItemLabelGenerator(TaskOld::getName);

        // Binder
        Binder<TimeEntryDto> binder = new Binder<>(TimeEntryDto.class);
        TimeEntryDto timeEntryDto = new TimeEntryDto();

        binder.forField(description).bind(TimeEntryDto::getDescription, TimeEntryDto::setDescription);
        binder.forField(startTime).bind(TimeEntryDto::getStartTime, TimeEntryDto::setStartTime);
        binder.forField(endTime).bind(TimeEntryDto::getEndTime, TimeEntryDto::setEndTime);
        binder.forField(tag).bind(TimeEntryDto::getTag, TimeEntryDto::setTag);
//        binder.forField(workerComboBox)
//                .asRequired("Worker is required")
//                .bind(TimeEntryDto::getWorker, TimeEntryDto::setWorker);
//        binder.forField(taskComboBox)
//                .asRequired("Task is required")
//                .bind(TimeEntryDto::getTaskId, TimeEntryDto::setTaskId);


        timeEntryDto.setWorkerId(currentUser.getWorker(manageUserService).getId());
        binder.readBean(timeEntryDto);

        // Save button
        Button saveButton = new Button("Create Time Entry");
        saveButton.addThemeVariants(ButtonVariant.LUMO_PRIMARY);
        saveButton.setDisableOnClick(true);

        saveButton.addClickListener(event -> {
            if (binder.validate().isOk()) {
                if (binder.writeBeanIfValid(timeEntryDto)) {
                    try {
                        TaskOld selectedTask = taskComboBox.getValue();
                        if (selectedTask != null) {
                            timeEntryDto.setTaskId(selectedTask.getId());
                        } else {
                            Notification.show("Please select a task.", 3000, Notification.Position.TOP_CENTER);
                            saveButton.setEnabled(true);
                            return;
                        }

                        manageTimeEntryService.createTimeEntry(timeEntryDto);
                        saveButton.setEnabled(true);
                        Notification.show("Time entry created successfully!", 3000, Notification.Position.TOP_CENTER);
                        clearForm(binder);
                        binder.readBean(new TimeEntryDto());
                    } catch (Exception e) {
                        saveButton.setEnabled(true);
                        Notification.show("Error creating time entry: " + e.getMessage(), 3000, Notification.Position.TOP_CENTER);
                    }
                }
            } else {
                saveButton.setEnabled(true);
            }
        });

        // Layout config
        formLayout.add(description, startTime, endTime, tag, taskComboBox, saveButton);
        add(formLayout);
    }

    private void clearForm(Binder<TimeEntryDto> binder) {
        TimeEntryDto timeEntryDto = new TimeEntryDto();
        binder.readBean(timeEntryDto);
    }
}
