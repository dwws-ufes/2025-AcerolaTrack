package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.model.Project;

import java.util.List;

public interface IManageProjectService {

    Project createProject(ProjectDto project);

    List<Project> findAll();
}
