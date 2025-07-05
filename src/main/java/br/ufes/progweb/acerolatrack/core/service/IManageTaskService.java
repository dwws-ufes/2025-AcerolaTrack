package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IManageTaskService {
    TaskOld saveTask(TaskDto task);

    Page<TaskOld> getAllTasks(Pageable pageable);

    TaskOld updateTask(Long id, TaskDto taskDto);

    void deleteTask(Long id);
}
