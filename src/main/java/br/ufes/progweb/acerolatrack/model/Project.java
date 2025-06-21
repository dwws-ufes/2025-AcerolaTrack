package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dueDate;
    private String description;
    private Status status;
    @ManyToOne()
    @JoinColumn(name = "custumer_id")
    private Customer customer;
    @ManyToMany()
    @JoinTable(
            name = "project_report",
            joinColumns = @JoinColumn(
                    name = "project_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "worker_id",
                    referencedColumnName = "id"
            )
    )
    private List<Worker> workers;
}
