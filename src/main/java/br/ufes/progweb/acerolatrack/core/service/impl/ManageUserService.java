package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageUserService;
import br.ufes.progweb.acerolatrack.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static br.ufes.progweb.acerolatrack.model.Worker.Role.WORKER;

@Service
@RequiredArgsConstructor
public class ManageUserService implements IManageUserService {

    private final WorkerRepository workerRepository;

    @Override
    public Worker saveWorker(Worker worker) {
        worker.setRole(WORKER);
        return workerRepository.save(worker);
    }

    @Override
    public Page<Worker> getAllWorkers(Pageable pageable) {
        return workerRepository.findByActiveTrue(pageable);
    }

    @Override
    public void deleteWorker(Long id) {
        Worker worker = workerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Worker not found with id: " + id));
        worker.setActive(false);
        workerRepository.save(worker);
    }

    @Override
    public Worker saveManager(Worker worker) {
        worker.setRole(Worker.Role.MANAGER);
        return workerRepository.save(worker);
    }
}
