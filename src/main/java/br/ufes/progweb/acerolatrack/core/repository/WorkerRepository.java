package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.Worker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
}
