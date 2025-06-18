package br.ufes.progweb.acerolatrack.model;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class TimeEntry {

    private Long id;
    private String description;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private String tag;
    private Integer totalTime;

}
