package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
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
}
