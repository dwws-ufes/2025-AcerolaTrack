package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.dto.ProjectUpdateDto;
import br.ufes.progweb.acerolatrack.model.Project;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IManageProjectService {

    Project createProject(ProjectDto project);

    List<Project> findAll();

    Page<Project> getAllProjects(Pageable pageable);

    Project updateProject(Long id, ProjectUpdateDto projectUpdateDto);
}
