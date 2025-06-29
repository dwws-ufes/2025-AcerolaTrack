package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.core.service.IManageTimeEntryService;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
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

}
