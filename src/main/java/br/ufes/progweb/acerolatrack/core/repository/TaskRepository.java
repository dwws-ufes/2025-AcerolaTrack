package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<TimeEntry, Long> {
}
