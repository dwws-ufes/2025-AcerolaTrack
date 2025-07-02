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
@RequestMapping("/v1/manager")
@RequiredArgsConstructor
@Slf4j
public class ManagerController {

    private final IManageUserService manageUserService;

    @PostMapping("/create")
    public Worker createManager(@RequestBody Worker worker) {
        log.info("Creating manager: {}", worker.getUsername());
        return manageUserService.saveManager(worker);
    }
}
