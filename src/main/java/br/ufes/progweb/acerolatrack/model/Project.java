package br.ufes.progweb.acerolatrack.model;

import br.ufes.progweb.acerolatrack.core.dto.ProjectDto;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
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

    public static Project of(ProjectDto projectDto, final Optional<Customer> customer, List<Worker> workers) {
        return Project.builder()
                .name(projectDto.getName())
                .startTime(projectDto.getStartTime())
                .endTime(projectDto.getEndTime())
                .dueDate(projectDto.getDueDate())
                .description(projectDto.getDescription())
                .status(projectDto.getStatus())
                .customer(customer.orElse(null))
                .workers(workers)
                .build();
    }
}
