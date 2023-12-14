package br.com.At.atjava.service;

import br.com.At.atjava.dto.TaskDTO;
import br.com.At.atjava.exception.TaskNotFoundException;
import br.com.At.atjava.model.Task;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Service
public class TaskService {

    private final Map<Long, Task> tasks = new ConcurrentHashMap<>();
    private final AtomicLong counter = new AtomicLong();

    @PostConstruct
    public void init() {
        IntStream.rangeClosed(1, 50).forEach(i -> {
            TaskDTO task = new TaskDTO();
            task.setDescription("Task " + i);
            task.setCompleted(i % 2 == 0);
            task.setTags(List.of("Tag" + i));
            createTask(task);
        });
    }

    public Task createTask(TaskDTO taskDTO) {
        Long id = counter.incrementAndGet();
        Task task = new Task(id, taskDTO.getDescription(), taskDTO.isCompleted(), taskDTO.getTags());
        tasks.put(id, task);
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public Task updateTask(Long id, TaskDTO taskDTO) {
        Task task = tasks.get(id);
        if (task == null) {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
        task.setDescription(taskDTO.getDescription());
        task.setCompleted(taskDTO.isCompleted());
        task.setTags(taskDTO.getTags());
        tasks.put(id, task);
        return task;
    }

    public void deleteTask(Long id) {
        if (!tasks.containsKey(id)) {
            throw new TaskNotFoundException("Task with id " + id + " not found");
        }
        tasks.remove(id);
    }

    public List<Task> getFilteredTasks(String tag, Boolean completed) {
        return getAllTasks().stream()
                .filter(task -> tag == null || (task.getTags() != null && task.getTags().contains(tag)))
                .filter(task -> completed == null || task.isCompleted() == completed)
                .collect(Collectors.toList());
    }
}
