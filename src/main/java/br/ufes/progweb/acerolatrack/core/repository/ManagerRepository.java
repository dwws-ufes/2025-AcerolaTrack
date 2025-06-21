package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.Manager;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ManagerRepository extends JpaRepository<Manager, Long> {
}
