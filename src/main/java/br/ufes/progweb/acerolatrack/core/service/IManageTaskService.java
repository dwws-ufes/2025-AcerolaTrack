package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.model.Task;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManageTaskService {
    Task saveTask(TaskDto task);

    Page<Task> getAllTasks(Pageable pageable);

    Task updateTask(Long id, TaskDto taskDto);

    void deleteTask(Long id);
}
