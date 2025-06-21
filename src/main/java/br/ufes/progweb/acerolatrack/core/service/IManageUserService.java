package br.ufes.progweb.acerolatrack.core.service;

import br.ufes.progweb.acerolatrack.model.Manager;
import br.ufes.progweb.acerolatrack.model.Worker;

public interface IManageUserService {
    Worker saveWorker(Worker worker);

    Manager saveManager(Manager manager);
}
