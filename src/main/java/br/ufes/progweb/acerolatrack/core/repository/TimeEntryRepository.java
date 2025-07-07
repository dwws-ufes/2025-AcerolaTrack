package br.ufes.progweb.acerolatrack.core.repository;

import br.ufes.progweb.acerolatrack.model.TimeEntry;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TimeEntryRepository extends JpaRepository<TimeEntry, Long> {
    @Query("SELECT te FROM TimeEntry te WHERE te.taskOld.project.id = :projectId")
    List<TimeEntry> findByTaskProjectId(Long projectId);
}
