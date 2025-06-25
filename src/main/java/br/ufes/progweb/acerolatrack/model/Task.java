package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    @OneToOne()
    @JoinColumn(name = "dependency_id", referencedColumnName = "id")
    private Task dependency;
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
}
