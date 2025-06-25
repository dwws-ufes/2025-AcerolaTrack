package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.repository.TaskRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Task;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageTaskService implements IManageTaskService {

    private final TaskRepository taskRepository;
    public Task saveTask(Task task) {
        return taskRepository.save(task);
    }
}
