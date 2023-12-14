package br.com.At.atjava.service;

import br.com.At.atjava.dto.TaskDTO;
import br.com.At.atjava.exception.TaskNotFoundException;
import br.com.At.atjava.model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
@SpringBootTest
public class TaskServiceTest {

    @InjectMocks
    private TaskService taskService;

    @BeforeEach
    void setUp() {

        taskService = new TaskService();


        TaskDTO task1 = new TaskDTO();
        task1.setDescription("Task 1");
        task1.setCompleted(false);
        task1.setTags(Arrays.asList("urgent", "home"));
        taskService.createTask(task1);

        TaskDTO task2 = new TaskDTO();
        task2.setDescription("Task 2");
        task2.setCompleted(true);
        task2.setTags(Arrays.asList("work"));
        taskService.createTask(task2);
    }

    @Test
    void whenDeleteTaskWithInvalidId_thenThrowTaskNotFoundException() {
        Long invalidId = 999L;
        assertThrows(TaskNotFoundException.class, () -> taskService.deleteTask(invalidId));
    }

    @Test
    void whenGetAllTasks_thenTasksListShouldNotBeEmpty() {
        List<Task> tasks = taskService.getAllTasks();
        assertNotNull(tasks);
        assertFalse(tasks.isEmpty());
    }


}
