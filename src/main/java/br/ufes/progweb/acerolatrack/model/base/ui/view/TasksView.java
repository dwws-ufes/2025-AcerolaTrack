package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.service.impl.ManageTaskService;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.icon.Icon;
import com.vaadin.flow.component.icon.VaadinIcon;
import com.vaadin.flow.component.notification.Notification;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.format.DateTimeFormatter;

@PermitAll
@Route("tasks")
public class TasksView extends VerticalLayout {

    private final ManageTaskService manageTaskService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Grid<TaskOld> grid;

    @Autowired
    public TasksView(ManageTaskService manageTaskService) {
        this.manageTaskService = manageTaskService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(getTasksGrid());
    }

    private Component getTasksGrid() {
        grid = new Grid<>(TaskOld.class, false);

        grid.addColumn(TaskOld::getName)
                .setHeader("Name")
                .setAutoWidth(true)
                .setFlexGrow(1);

        grid.addColumn(task -> task.getProject() != null ? task.getProject().getName() : "-")
                .setHeader("Project")
                .setAutoWidth(true);

        grid.addColumn(task -> task.getDependency() != null ? task.getDependency().getName() : "-")
                .setHeader("Dependency")
                .setAutoWidth(true);

        grid.addColumn(task -> task.getStartTime() != null ? task.getStartTime().format(DATE_FORMATTER) : "-")
                .setHeader("Start Time")
                .setAutoWidth(true);

        grid.addColumn(task -> task.getEndTime() != null ? task.getEndTime().format(DATE_FORMATTER) : "-")
                .setHeader("End Time")
                .setAutoWidth(true);

//        grid.addColumn(task -> task.getWorkers() != null ?
//                task.getWorkers().stream()
//                    .map(worker -> worker.getUsername())
//                    .reduce((a, b) -> a + ", " + b)
//                    .orElse("-") : "-")
//                .setHeader("Workers")
//                .setAutoWidth(true);

        grid.addComponentColumn(task -> {
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            deleteButton.addClickListener(e -> confirmDelete(task));
            return deleteButton;
        }).setHeader("Actions").setAutoWidth(true);

        refreshGrid();
        return grid;
    }

    private void confirmDelete(TaskOld task) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirm Delete");
        dialog.setText("Are you sure you want to delete this task?");

        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {
            try {
                manageTaskService.deleteTask(task.getId());
                Notification.show("Task successfully deleted", 3000, Notification.Position.TOP_CENTER);
                refreshGrid();
            } catch (Exception ex) {
                Notification.show("Error deleting task: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER);
            }
        });

        dialog.open();
    }

    private void refreshGrid() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TaskOld> tasks = manageTaskService.getAllTasks(pageable);
        grid.setItems(tasks.getContent());
    }
}
