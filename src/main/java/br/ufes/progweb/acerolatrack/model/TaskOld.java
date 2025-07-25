package br.ufes.progweb.acerolatrack.model;

import br.ufes.progweb.acerolatrack.core.dto.TaskDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TaskOld extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToOne()
    @JoinColumn(name = "dependency_id", referencedColumnName = "id")
    private TaskOld dependency;
    @ManyToOne()
    @JoinColumn(name = "project_id", referencedColumnName = "id")
    private Project project;
    @ManyToMany()
    @JoinTable(
            name = "task_workers_join",
            joinColumns = @JoinColumn(
                    name = "task_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "worker_id",
                    referencedColumnName = "id"
            )
    )
    private List<Worker> workers;

    @Builder.Default
    private boolean cancelled = false;

    public TaskOld of(TaskDto taskDto) {
        return TaskOld.builder()
                .name(taskDto.getName())
                .startTime(taskDto.getStartTime())
                .endTime(taskDto.getEndTime())
                .build();
    }
}