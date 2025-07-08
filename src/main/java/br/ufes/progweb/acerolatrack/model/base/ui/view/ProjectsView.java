package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.model.Customer;
import br.ufes.progweb.acerolatrack.model.Project;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.button.ButtonVariant;
import com.vaadin.flow.component.confirmdialog.ConfirmDialog;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
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
@Route("projects")
public class ProjectsView extends VerticalLayout {

    private final ManageProjectService manageProjectService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Grid<Project> grid;

    @Autowired
    public ProjectsView(ManageProjectService manageProjectService) {
        this.manageProjectService = manageProjectService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(getProjectsGrid());
    }

    public Component getProjectsGrid() {
        grid = new Grid<>(Project.class, false);

        grid.addColumn(Project::getName)
                .setHeader("Name")
                .setAutoWidth(true)
                .setFlexGrow(1);
        grid.addColumn(Project::getDescription)
                .setHeader("Description")
                .setAutoWidth(true)
                .setFlexGrow(1);

        // Adiciona coluna de ações com botão delete
        grid.addComponentColumn(project -> {
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            deleteButton.addClickListener(e -> confirmDelete(project));
            return deleteButton;
        }).setHeader("Actions").setAutoWidth(true);

        grid.addItemClickListener(event -> showProjectDetails(event.getItem()));

        refreshGrid();
        return grid;
    }

    private void showProjectDetails(Project project) {
        Dialog dialog = new Dialog();
        dialog.setHeaderTitle("Project Details");

        VerticalLayout layout = new VerticalLayout();
        layout.add(new Span("Name: " + project.getName()));
        layout.add(new Span("Description: " + project.getDescription()));
        layout.add(new Span("Status: " + project.getStatus()));
        layout.add(new Span("Start Time: " + (project.getStartTime() != null ? project.getStartTime().format(DATE_FORMATTER) : "-")));
        layout.add(new Span("End Time: " + (project.getEndTime() != null ? project.getEndTime().format(DATE_FORMATTER) : "-")));
        layout.add(new Span("Due Date: " + (project.getDueDate() != null ? project.getDueDate().format(DATE_FORMATTER) : "-")));

        Button closeButton = new Button("Close", e -> dialog.close());
        layout.add(closeButton);

        dialog.add(layout);
        dialog.open();
    }

    private void confirmDelete(Project project) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirm Delete");
        dialog.setText("Are you sure you want to delete this project?");

        dialog.setCancelable(true);
        dialog.setCancelText("Cancel");

        dialog.setConfirmText("Delete");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {
            try {
                manageProjectService.deleteProject(project.getId());
                Notification.show("Project successfully deleted", 3000, Notification.Position.TOP_CENTER);
                refreshGrid();
            } catch (Exception ex) {
                Notification.show("Error deleting project: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER);
            }
        });

        dialog.open();
    }

    private void refreshGrid() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Project> projects = manageProjectService.getAllProjects(pageable);
        grid.setItems(projects.getContent());
    }
}
