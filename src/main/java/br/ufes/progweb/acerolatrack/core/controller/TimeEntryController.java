package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.core.service.IManageTimeEntryService;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @GetMapping
    public Page<TimeEntry> getAllTimeEntries(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return manageTimeEntryService.getAllTimeEntries(pageRequest);
    }

}
