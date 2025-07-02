package br.ufes.progweb.acerolatrack.core.dto;

import br.ufes.progweb.acerolatrack.model.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectUpdateDto {
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dueDate;
    private String description;
    private Status status;
    private Long customerId;
    private List<Long> workerIds;
}
