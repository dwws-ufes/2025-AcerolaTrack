package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Entity
public class TimeEntry {

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
    @ManyToMany()
    @JoinTable(
            name = "time_entry_join",
            joinColumns = @JoinColumn(
                    name = "time_entry_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "task_id",
                    referencedColumnName = "id"
            )
    )
    private List<Task> tasks;

}
