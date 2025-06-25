package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.service.IManageTaskService;
import br.ufes.progweb.acerolatrack.model.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/tasks")
@RequiredArgsConstructor
@Slf4j
public class TaskController {
    private final IManageTaskService manageTaskService;

    @PostMapping("/create")
    public Task createTask(Task task) {
        log.info("Creating task: {}", task.getName());
        return manageTaskService.saveTask(task);
    }
}
