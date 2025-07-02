package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final IManageTaskService manageTaskService;

    @PostMapping("/create")
    public Task createTask(@RequestBody TaskDto taskDto) {
        log.info("Creating task: {}", taskDto.getName());
        return manageTaskService.saveTask(taskDto);
    }

    @GetMapping
    public Page<Task> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return manageTaskService.getAllTasks(pageRequest);
    }

    @PutMapping("/{id}")
    public Task updateTask(@PathVariable Long id, @RequestBody TaskDto taskDto) {
        log.info("Updating task with id: {}", id);
        return manageTaskService.updateTask(id, taskDto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteTask(@PathVariable Long id) {
        log.info("Cancelling task with id: {}", id);
        manageTaskService.deleteTask(id);
    }
}
