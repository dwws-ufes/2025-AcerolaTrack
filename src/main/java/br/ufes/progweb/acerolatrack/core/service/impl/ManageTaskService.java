package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.repository.ProjectRepository;
import br.ufes.progweb.acerolatrack.core.repository.TaskRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.TaskOld;
import br.ufes.progweb.acerolatrack.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManageTaskService implements IManageTaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final WorkerRepository workerRepository;

    public TaskOld saveTask(TaskDto taskDto) {
        var dependency = getDependency(taskDto);
        var project = getProject(taskDto);
        var workers = getWorkers(taskDto);

        //validar se precisa de alguma validação aqui

        var task = getTaskFromDto(taskDto, dependency, project, workers);

        return taskRepository.save(task);
    }

    @Override
    public Page<TaskOld> getAllTasks(Pageable pageable) {
        return taskRepository.findByCancelledFalse(pageable);
    }

    private Optional<TaskOld> getDependency(TaskDto taskDto) {
        return taskDto.getDependencyId() != null ? taskRepository.findById(taskDto.getDependencyId()) : Optional.empty();
    }

    private Optional<Project> getProject(TaskDto taskDto) {
        return taskDto.getProjectId() != null ? projectRepository.findById(taskDto.getProjectId()) : Optional.empty();
    }

    private List<Worker> getWorkers(TaskDto taskDto) {
        return taskDto.getWorkerIds().stream()
                .map(workerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();
    }

    private TaskOld getTaskFromDto(TaskDto taskDto, Optional<TaskOld> dependency, Optional<Project> project, List<Worker> workers) {
        return TaskOld.builder()
                .name(taskDto.getName())
                .startTime(taskDto.getStartTime())
                .endTime(taskDto.getEndTime())
                .dependency(dependency.orElse(null))
                .project(project.orElse(null))
                .workers(workers)
                .build();
    }

    @Override
    public TaskOld updateTask(Long id, TaskDto taskDto) {
        TaskOld existingTaskOld = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        var dependency = getDependency(taskDto);
        var project = getProject(taskDto);
        var workers = getWorkers(taskDto);

        if (taskDto.getName() != null) {
            existingTaskOld.setName(taskDto.getName());
        }
        existingTaskOld.setStartTime(taskDto.getStartTime());
        existingTaskOld.setEndTime(taskDto.getEndTime());
        existingTaskOld.setDependency(dependency.orElse(null));
        existingTaskOld.setProject(project.orElse(null));
        existingTaskOld.setWorkers(workers);

        return taskRepository.save(existingTaskOld);
    }

    @Override
    public void deleteTask(Long id) {
        TaskOld taskOld = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));
        taskOld.setCancelled(true);
        taskRepository.save(taskOld);
    }
}
