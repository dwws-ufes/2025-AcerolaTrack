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
    public List<Project> findAll() {
        return projectRepository.findAll();
    }

    @Override
    public Page<Project> getAllProjects(Pageable pageable) {
        return projectRepository.findAll(pageable);
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

        // Update basic fields if they are not null
        if (projectUpdateDto.getName() != null) {
            existingProject.setName(projectUpdateDto.getName());
        }
        if (projectUpdateDto.getStartTime() != null) {
            existingProject.setStartTime(projectUpdateDto.getStartTime());
        }
        if (projectUpdateDto.getEndTime() != null) {
            existingProject.setEndTime(projectUpdateDto.getEndTime());
        }
        if (projectUpdateDto.getDueDate() != null) {
            existingProject.setDueDate(projectUpdateDto.getDueDate());
        }
        if (projectUpdateDto.getDescription() != null) {
            existingProject.setDescription(projectUpdateDto.getDescription());
        }
        if (projectUpdateDto.getStatus() != null) {
            existingProject.setStatus(projectUpdateDto.getStatus());
        }

        // Update customer if customerId is provided
        if (projectUpdateDto.getCustomerId() != null) {
            Customer customer = customerRepository.findById(projectUpdateDto.getCustomerId())
                    .orElseThrow(() -> new RuntimeException("Customer not found with id: " + projectUpdateDto.getCustomerId()));
            existingProject.setCustomer(customer);
        }

        // Update workers if workerIds is provided
        if (projectUpdateDto.getWorkerIds() != null && !projectUpdateDto.getWorkerIds().isEmpty()) {
            List<Worker> workers = workerRepository.findAllById(projectUpdateDto.getWorkerIds());
            existingProject.setWorkers(workers);
        }

        return projectRepository.save(existingProject);
    }
}
