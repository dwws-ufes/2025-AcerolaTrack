package br.ufes.progweb.acerolatrack.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Map;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ProjectReport {
    private Long projectId;
    private String projectName;
    private Map<String, Integer> minutesPerWorker;
    private Integer total;
}
