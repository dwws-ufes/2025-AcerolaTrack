package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.service.impl.ManageProjectService;
import br.ufes.progweb.acerolatrack.model.Customer;
import br.ufes.progweb.acerolatrack.model.Project;
import com.vaadin.flow.component.Component;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Span;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;
import com.vaadin.flow.theme.lumo.LumoUtility;
import jakarta.annotation.security.PermitAll;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@PermitAll
@Route("projects")
public class ProjectsView extends VerticalLayout {

    private final ManageProjectService manageProjectService;

    @Autowired
    public ProjectsView(ManageProjectService manageProjectService) {
        this.manageProjectService = manageProjectService;

        addClassName(LumoUtility.Padding.MEDIUM);
        add(getProjectsGrid());
    }

    public Component getProjectsGrid() {
        // Busca página de projetos com paginação
        Pageable pageable = PageRequest.of(0, 10);
        Page<Project> projectPage = manageProjectService.getAllProjects(pageable);

        // Cria grid
        Grid<Project> grid = new Grid<>(Project.class, false);

        // Define colunas
        grid.addColumn(Project::getName).setHeader("Name")
                .setAutoWidth(true)  // ajusta a largura para o conteúdo
                .setFlexGrow(1);     // permite expandir proporcionalmente
        grid.addColumn(Project::getDescription).setHeader("Description")
            .setAutoWidth(true)  // ajusta a largura para o conteúdo
                .setFlexGrow(1);     // permite expandir proporcionalmente
//        grid.addColumn(Project::getStatus).setHeader("Status");
//        grid.addColumn(project -> {
//            Customer customer = project.getCustomer();
//            return customer != null ? customer.getName() : "";
//        }).setHeader("Customer");
//        grid.addColumn(Project::getStartTime).setHeader("Start Time");
//        grid.addColumn(Project::getEndTime).setHeader("End Time");
//        grid.addColumn(Project::getDueDate).setHeader("Due Date");

        grid.addItemClickListener(event -> {
            Project project = event.getItem();

            Dialog dialog = new Dialog();
            dialog.setHeaderTitle("Project Details");

            VerticalLayout layout = new VerticalLayout();
            layout.add(new Span("Name: " + project.getName()));
            layout.add(new Span("Description: " + project.getDescription()));
            layout.add(new Span("Status: " + project.getStatus()));
            layout.add(new Span("Start Time: " + project.getStartTime()));
            layout.add(new Span("End Time: " + project.getEndTime()));
            layout.add(new Span("Due Date: " + project.getDueDate()));

            // Botão de fechar
            Button closeButton = new Button("Close", e -> dialog.close());
            layout.add(closeButton);

            dialog.add(layout);
            dialog.open();
        });

        // Define itens
        grid.setItems(projectPage.getContent());

        return grid;
    }
}
