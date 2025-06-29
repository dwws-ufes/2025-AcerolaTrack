package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.service.IManageUserService;
import br.ufes.progweb.acerolatrack.model.Worker;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/workers")
@RequiredArgsConstructor
@Slf4j
public class WorkerController {
    private final IManageUserService manageUserService;

    @PostMapping("/create")
    private Worker createWorker(@RequestBody Worker worker) {
        log.info("Creating worker: {}", worker.getUsername());
        return manageUserService.saveWorker(worker);
    }
}
