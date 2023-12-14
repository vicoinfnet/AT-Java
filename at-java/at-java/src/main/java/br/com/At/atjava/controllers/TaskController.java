package br.com.At.atjava.controllers;

import br.com.At.atjava.dto.TaskDTO;
import br.com.At.atjava.model.Task;
import br.com.At.atjava.service.TaskService;

import br.com.At.atjava.model.Endereco;
import br.com.At.atjava.service.ViaCepService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import java.io.IOException;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @PostMapping
    public ResponseEntity<Task> createTask(@RequestBody TaskDTO taskDTO) {
        Task task = taskService.createTask(taskDTO);
        return ResponseEntity.ok(task);
    }

    @GetMapping
    public ResponseEntity<List<Task>> getAllTasks(
            @RequestParam(required = false) String tag,
            @RequestParam(required = false) Boolean completed) {
        List<Task> tasks = taskService.getFilteredTasks(tag, completed);
        return ResponseEntity.ok(tasks);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Task> updateTask(@PathVariable Long id, @RequestBody TaskDTO taskDTO) {
        Task updatedTask = taskService.updateTask(id, taskDTO);
        return ResponseEntity.ok(updatedTask);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @Autowired
    private ViaCepService viaCepService;

    @GetMapping("/endereco")
    public ResponseEntity<Endereco> getEnderecoByCep(@RequestParam String cep) {
        try {
            Endereco endereco = viaCepService.getEndereco(cep);
            return ResponseEntity.ok(endereco);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().build();
        }
    }

}
