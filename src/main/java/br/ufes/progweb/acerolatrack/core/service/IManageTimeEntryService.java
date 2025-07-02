package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IManageTimeEntryService {
    TimeEntry createTimeEntry(TimeEntryDto timeEntryDto);

    Page<TimeEntry> getAllTimeEntries(Pageable pageable);

    TimeEntry updateTimeEntry(Long id, TimeEntryDto timeEntryDto);
}
