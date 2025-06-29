package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import br.ufes.progweb.acerolatrack.core.repository.TaskRepository;
import br.ufes.progweb.acerolatrack.core.repository.TimeEntryRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageTimeEntryService;
import br.ufes.progweb.acerolatrack.model.Task;
import br.ufes.progweb.acerolatrack.model.TimeEntry;
import br.ufes.progweb.acerolatrack.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManageTimeEntryService implements IManageTimeEntryService {

    private final TimeEntryRepository timeEntryRepository;
    private final WorkerRepository workerRepository;
    private final TaskRepository taskRepository;

    @Override
    public TimeEntry createTimeEntry(final TimeEntryDto timeEntryDto) {
        var worker = getWorker(timeEntryDto.getWorkerId());
        var task = getTask(timeEntryDto.getTaskId());

        return timeEntryRepository.save(TimeEntry.of(timeEntryDto, worker, task));
    }

    private Optional<Worker> getWorker(final Long workerId) {
        return workerId != null ? workerRepository.findById(workerId) : null;
    }

    private Optional<Task> getTask(final Long taskId) {
        return taskId != null ? taskRepository.findById(taskId) : null;
    }

    @Override
    public Page<TimeEntry> getAllTimeEntries(Pageable pageable) {
        return timeEntryRepository.findAll(pageable);
    }

}
