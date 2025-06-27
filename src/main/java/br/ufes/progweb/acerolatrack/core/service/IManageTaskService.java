package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.model.Task;

import java.util.List;

public interface IManageTaskService {
    Task saveTask(TaskDto task);

    List<Task> findAll();
}
