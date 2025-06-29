package br.ufes.progweb.acerolatrack.model;

import br.ufes.progweb.acerolatrack.core.dto.TimeEntryDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Optional;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TimeEntry extends AuditEntity {

    @Id()
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tag;
    private Integer totalTime;
    @ManyToOne()
    @JoinColumn(name = "worker_id", referencedColumnName = "id")
    private Worker worker;
    @ManyToOne()
    @JoinColumn(name = "task_id", referencedColumnName = "id")
    private Task task;

    public static TimeEntry of(TimeEntryDto timeEntryDto, Optional<Worker> worker, Optional<Task> tasks) {
        return TimeEntry.builder()
                .id(timeEntryDto.getId())
                .description(timeEntryDto.getDescription())
                .startTime(timeEntryDto.getStartTime())
                .endTime(timeEntryDto.getEndTime())
                .tag(timeEntryDto.getTag())
                .totalTime(timeEntryDto.getTotalTime())
                .worker(worker.orElse(null))
                .task(tasks.orElse(null))
                .build();
    }
}
