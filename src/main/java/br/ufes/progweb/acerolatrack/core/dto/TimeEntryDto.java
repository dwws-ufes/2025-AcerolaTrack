package br.ufes.progweb.acerolatrack.core.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeEntryDto {
    private Long id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tag;
    private Integer totalTime;
    private Long workerId;
    private Long taskId;
}
