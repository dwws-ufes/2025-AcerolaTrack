package br.ufes.progweb.acerolatrack.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
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
    private boolean active = true;
    private Role role;

    public enum Role {
        WORKER,
        MANAGER
    }
}

