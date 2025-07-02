package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import br.ufes.progweb.acerolatrack.core.dto.ProjectUpdateDto;
import br.ufes.progweb.acerolatrack.core.repository.CustomerRepository;
import br.ufes.progweb.acerolatrack.core.repository.ProjectRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageProjectService;
import br.ufes.progweb.acerolatrack.model.Customer;
import br.ufes.progweb.acerolatrack.model.Project;
import br.ufes.progweb.acerolatrack.model.Worker;
import br.ufes.progweb.acerolatrack.model.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ManageProjectService implements IManageProjectService {

    private final ProjectRepository projectRepository;
    private final CustomerRepository customerRepository;
    private final WorkerRepository workerRepository;


    @Override
    public Project createProject(ProjectDto projectDto) {
        var customer = getCustomer(projectDto.getCustomerId());
        var workers = getWorkers(projectDto.getWorkerIds());

        return projectRepository.save(Project.of(projectDto, customer, workers));
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findByStatusNot(Status.CANCELLED, pageable);
    }

    private Optional<Customer> getCustomer(final Long customerId) {
        return customerId != null ? customerRepository.findById(customerId) : null;

    }

    private List<Worker> getWorkers(final List<Long> workerIds) {
        return workerIds.stream()
                .map(workerRepository::findById)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

    }

    @Override
    public Project updateProject(Long id, ProjectUpdateDto projectUpdateDto) {
        Project existingProject = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));

        if (projectUpdateDto.getName() != null) {
            existingProject.setName(projectUpdateDto.getName());
        }

        existingProject.setStartTime(projectUpdateDto.getStartTime());
        existingProject.setEndTime(projectUpdateDto.getEndTime());
        existingProject.setDueDate(projectUpdateDto.getDueDate());

        if (projectUpdateDto.getDescription() != null) {
            existingProject.setDescription(projectUpdateDto.getDescription());
        }
            existingProject.setStatus(projectUpdateDto.getStatus());

        if (projectUpdateDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(projectUpdateDto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + projectUpdateDto.getCustomerId()));
            existingProject.setCustomer(customer);
        } else {
            existingProject.setCustomer(null);
        }

        if (projectUpdateDto.getWorkerIds() != null && !projectUpdateDto.getWorkerIds().isEmpty()) {
            List<Worker> workers = workerRepository.findAllById(projectUpdateDto.getWorkerIds());
            existingProject.setWorkers(workers);
        } else {
            existingProject.setWorkers(null);
        }

        return projectRepository.save(existingProject);
    }

    @Override
    public void deleteProject(Long id) {
        Project project = projectRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Project not found with id: " + id));
        project.setStatus(Status.CANCELLED);
        projectRepository.save(project);
    }
}
