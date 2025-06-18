package br.ufes.progweb.acerolatrack.model;

import java.time.LocalDateTime;

public class Project {

    private Long id;
    private String name;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private LocalDateTime dueDate;
    private String description;
    private Status status;
}
