package br.ufes.progweb.acerolatrack.core.controller;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.dto.ProjectUpdateDto;
import br.ufes.progweb.acerolatrack.core.service.IManageProjectService;
import br.ufes.progweb.acerolatrack.model.Project;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/projects")
@RequiredArgsConstructor
@Slf4j
public class ProjectController {

    private final IManageProjectService manageProjectService;

    @PostMapping("/create")
    public Project createProject(@RequestBody ProjectDto projectDto) {
        log.info("Creating project: {}", projectDto.getName());
        return manageProjectService.createProject(projectDto);
    }

    @GetMapping
    public Page<Project> getAllProjects(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        PageRequest pageRequest = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "createdAt"));
        return manageProjectService.getAllProjects(pageRequest);
    }

    @PutMapping("/{id}")
    public Project updateProject(
            @PathVariable Long id,
            @RequestBody ProjectUpdateDto projectUpdateDto) {
        return manageProjectService.updateProject(id, projectUpdateDto);
    }
}
