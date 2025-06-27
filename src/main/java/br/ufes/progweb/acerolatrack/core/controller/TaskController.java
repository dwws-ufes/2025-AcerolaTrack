package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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

    @GetMapping()
    public List<Task> getTasks() {
        return manageTaskService.findAll();
    }
}
