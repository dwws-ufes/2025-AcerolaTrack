package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.repository.ProjectRepository;
import br.ufes.progweb.acerolatrack.core.repository.TaskRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.Task;
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

    public Task saveTask(TaskDto taskDto) {
        var dependency = getDependency(taskDto);
        var project = getProject(taskDto);
        var workers = getWorkers(taskDto);

        //validar se precisa de alguma validação aqui

        var task = getTaskFromDto(taskDto, dependency, project, workers);

        return taskRepository.save(task);
    }

    public List<Task> findAll() {
        return taskRepository.findAll();
    }

    @Override
    public Page<Task> getAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    private Optional<Task> getDependency(TaskDto taskDto) {
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

    private Task getTaskFromDto(TaskDto taskDto, Optional<Task> dependency, Optional<Project> project, List<Worker> workers) {
        return Task.builder()
                .name(taskDto.getName())
                .startTime(taskDto.getStartTime())
                .endTime(taskDto.getEndTime())
                .dependency(dependency.orElse(null))
                .project(project.orElse(null))
                .workers(workers)
                .build();
    }

    @Override
    public Task updateTask(Long id, TaskDto taskDto) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Task not found with id: " + id));

        var dependency = getDependency(taskDto);
        var project = getProject(taskDto);
        var workers = getWorkers(taskDto);

        if (taskDto.getName() != null) {
            existingTask.setName(taskDto.getName());
        }
        existingTask.setStartTime(taskDto.getStartTime());
        existingTask.setEndTime(taskDto.getEndTime());
        existingTask.setDependency(dependency.orElse(null));
        existingTask.setProject(project.orElse(null));
        existingTask.setWorkers(workers);

        return taskRepository.save(existingTask);
    }
}
