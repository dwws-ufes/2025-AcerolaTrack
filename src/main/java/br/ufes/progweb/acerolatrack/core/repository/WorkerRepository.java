package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.Worker;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkerRepository extends JpaRepository<Worker, Long> {
    Page<Worker> findByActiveTrue(Pageable pageable);
}
