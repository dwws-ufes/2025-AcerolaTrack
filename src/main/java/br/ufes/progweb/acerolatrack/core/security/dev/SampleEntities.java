package br.ufes.progweb.acerolatrack.core.security.dev;

import br.ufes.progweb.acerolatrack.core.repository.ProjectRepository;
import br.ufes.progweb.acerolatrack.core.repository.TaskRepository;
import br.ufes.progweb.acerolatrack.core.repository.WorkerRepository;
import br.ufes.progweb.acerolatrack.model.*;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.time.LocalDateTime;
import java.util.Arrays;


@Configuration
@Profile("dev")
public class SampleEntities {

    @Bean
    CommandLineRunner initDatabase(WorkerRepository workerRepository,
                                 ProjectRepository projectRepository,
                                 TaskRepository taskRepository) {
        return args -> {
            // Verificar se já existem dados
            if (workerRepository.count() > 0) {
                return; // Banco já possui dados
            }

            // Create Workers
            Worker manager = new Worker();
            manager.setUsername("john.manager");
            manager.setEmail("john.manager@example.com"); // campo obrigatório
            manager.setPassword("password"); // campo obrigatório
            manager.setRole(Worker.Role.MANAGER);
            manager.setActive(true);

            Worker worker1 = new Worker();
            worker1.setUsername("alice.dev");
            worker1.setEmail("alice.dev@example.com");
            worker1.setPassword("password");
            worker1.setRole(Worker.Role.WORKER);
            worker1.setActive(true);

            Worker worker2 = new Worker();
            worker2.setUsername("bob.dev");
            worker2.setEmail("bob.dev@example.com");
            worker2.setPassword("password");
            worker2.setRole(Worker.Role.WORKER);
            worker2.setActive(true);

            // Salvar workers primeiro
            var savedWorkers = workerRepository.saveAll(Arrays.asList(manager, worker1, worker2));
            
            // Create Project
            Project project = Project.builder()
                    .name("AcerolaTrack Development")
                    .description("Time tracking system development")
                    .startTime(LocalDateTime.now())
                    .dueDate(LocalDateTime.now().plusMonths(3))
                    .status(Status.OPEN)
                    .workers(savedWorkers)
                    .build();

            var savedProject = projectRepository.save(project);

            // Create Tasks
            Task task1 = Task.builder()
                    .name("Setup Initial Project Structure")
                    .startTime(LocalDateTime.now())
                    .endTime(LocalDateTime.now().plusDays(5))
                    .project(savedProject)
                    .workers(Arrays.asList(savedWorkers.get(0), savedWorkers.get(1)))
                    .cancelled(false)
                    .build();

            var savedTask1 = taskRepository.save(task1);

            Task task2 = Task.builder()
                    .name("Implement User Authentication")
                    .startTime(LocalDateTime.now().plusDays(1))
                    .endTime(LocalDateTime.now().plusDays(7))
                    .project(savedProject)
                    .workers(Arrays.asList(savedWorkers.get(2)))
                    .dependency(savedTask1)
                    .cancelled(false)
                    .build();

            taskRepository.save(task2);
        };
    }
}