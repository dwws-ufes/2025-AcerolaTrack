package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Worker extends User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) //provavelmente tem que mudar de estrategia depois
    private Long id;
    @ManyToMany
    @JoinTable(
            name = "project_report",
            joinColumns = @JoinColumn(
                    name = "worker_id",
                    referencedColumnName = "id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "project_id",
                    referencedColumnName = "id"
            )
    )
    private List<Project> projects;
}
