package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IManageUserService {
    Worker saveWorker(Worker worker);

    Page<Worker> getAllWorkers(Pageable pageable);

    void deleteWorker(Long id);

    Worker saveManager(Worker worker);
}
