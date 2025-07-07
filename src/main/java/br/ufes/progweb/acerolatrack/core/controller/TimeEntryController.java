package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.core.service.IManageTimeEntryService;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
import com.vaadin.flow.server.auth.AnonymousAllowed;
import com.vaadin.hilla.Endpoint;
import jakarta.annotation.security.PermitAll;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@Endpoint
@AnonymousAllowed
@RestController
@PermitAll
@RequestMapping("/v1/time-entries")
@Slf4j
@RequiredArgsConstructor
public class TimeEntryController {

    private final IManageTimeEntryService manageTimeEntryService;

    @PostMapping("/create")
    public TimeEntry createTimeEntry(@RequestBody TimeEntryDto timeEntryDto) {
        log.info("Creating time entry: {}", timeEntryDto.getDescription());
        // Logic to create a time entry would go here
        return manageTimeEntryService.createTimeEntry(timeEntryDto);
    }

    @GetMapping
    public Page<TimeEntry> getAllTimeEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return manageTimeEntryService.getAllTimeEntries(pageRequest);
    }

    @PutMapping("/{id}")
    public TimeEntry updateTimeEntry(
            @PathVariable Long id,
            @RequestBody TimeEntryDto timeEntryDto) {
        log.info("Updating time entry with id: {}", id);
        return manageTimeEntryService.updateTimeEntry(id, timeEntryDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTimeEntry(@PathVariable Long id) {
        log.info("Deleting time entry with id: {}", id);
        manageTimeEntryService.deleteTimeEntry(id);
    }
}
