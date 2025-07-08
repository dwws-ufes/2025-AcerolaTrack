package br.ufes.progweb.acerolatrack.model.base.ui.view;

import br.ufes.progweb.acerolatrack.core.service.impl.ManageTimeEntryService;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
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
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@PermitAll
@Route("time-entries")
public class TimeEntryView extends VerticalLayout {

    private final ManageTimeEntryService manageTimeEntryService;
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
    private Grid<TimeEntry> grid;

    @Autowired
    public TimeEntryView(ManageTimeEntryService manageTimeEntryService) {
        this.manageTimeEntryService = manageTimeEntryService;
        addClassName(LumoUtility.Padding.MEDIUM);
        add(getTimeEntryGrid());
    }

    private Component getTimeEntryGrid() {
        grid = new Grid<>(TimeEntry.class, false);

        grid.addColumn(TimeEntry::getDescription)
                .setHeader("Description")
                .setHeader("Start Time")
                .setAutoWidth(true)  // ajusta a largura para o conteúdo
                .setFlexGrow(1);     // permite expandir proporcionalmente
        grid.addColumn(entry -> entry.getStartTime().format(DATE_FORMATTER))
                .setHeader("Start Time")
                .setAutoWidth(true)  // ajusta a largura para o conteúdo
                .setFlexGrow(1);     // permite expandir proporcionalmente
        grid.addColumn(entry -> entry.getEndTime().format(DATE_FORMATTER))
                .setHeader("End Time")
                .setAutoWidth(true)  // ajusta a largura para o conteúdo
                .setFlexGrow(1);     // permite expandir proporcionalmente
        grid.addColumn(TimeEntry::getTag)
                .setHeader("Tag");
        grid.addColumn(TimeEntry::getTotalTime)
                .setHeader("Time (minutes)");
        grid.addColumn(entry -> entry.getWorker() == null ? "" : entry.getWorker().getUsername())
                .setHeader("Worker");
        grid.addColumn(entry -> entry.getTaskOld() == null ? "" : entry.getTaskOld().getName())
                .setHeader("Task");

        // Adiciona coluna de ações com botão delete
        grid.addComponentColumn(entry -> {
            Button deleteButton = new Button(new Icon(VaadinIcon.TRASH));
            deleteButton.addThemeVariants(ButtonVariant.LUMO_ERROR, ButtonVariant.LUMO_SMALL);
            deleteButton.addClickListener(e -> confirmDelete(entry));
            return deleteButton;
        }).setHeader("Actions").setAutoWidth(true);

        refreshGrid();
        return grid;
    }

    private void confirmDelete(TimeEntry timeEntry) {
        ConfirmDialog dialog = new ConfirmDialog();
        dialog.setHeader("Confirmar exclusão");
        dialog.setText("Tem certeza que deseja excluir este registro de tempo?");

        dialog.setCancelable(true);
        dialog.setCancelText("Cancelar");

        dialog.setConfirmText("Excluir");
        dialog.setConfirmButtonTheme("error primary");

        dialog.addConfirmListener(event -> {
            try {
                manageTimeEntryService.deleteTimeEntry(timeEntry.getId());
                Notification.show("Registro de tempo excluído com sucesso", 3000, Notification.Position.TOP_CENTER);
                refreshGrid();
            } catch (Exception ex) {
                Notification.show("Erro ao excluir registro: " + ex.getMessage(), 3000, Notification.Position.TOP_CENTER);
            }
        });

        dialog.open();
    }

    private void refreshGrid() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<TimeEntry> timeEntries = manageTimeEntryService.getAllTimeEntries(pageable);
        grid.setItems(timeEntries.getContent());
    }
}
