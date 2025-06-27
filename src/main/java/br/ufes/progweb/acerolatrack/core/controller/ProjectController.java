package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.service.IManageProjectService;
import br.ufes.progweb.acerolatrack.model.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("/v1/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final IManageProjectService manageProjectService;

    @PostMapping("/create")
    public Project createProject(@RequestBody ProjectDto projectDto) {
        log.info("Creating project: {}", projectDto.getName());
        return manageProjectService.createProject(projectDto);
    }
}
