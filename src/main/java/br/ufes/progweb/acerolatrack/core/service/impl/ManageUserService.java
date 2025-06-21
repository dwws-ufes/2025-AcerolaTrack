package br.ufes.progweb.acerolatrack.core.service.impl;

import br.ufes.progweb.acerolatrack.core.repository.ManagerRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.core.service.IManageUserService;
import br.ufes.progweb.acerolatrack.model.Manager;
import br.ufes.progweb.acerolatrack.model.Worker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ManageUserService implements IManageUserService {

    private final WorkerRepository workerRepository;
    private final ManagerRepository managerRepository;

    @Override
    public Worker saveWorker(Worker worker) {
        return workerRepository.save(worker);
    }

    @Override
    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

}
