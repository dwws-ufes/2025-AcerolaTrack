package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.model.TimeEntry;

public interface IManageTimeEntryService {
    TimeEntry createTimeEntry(TimeEntryDto timeEntryDto);
}
