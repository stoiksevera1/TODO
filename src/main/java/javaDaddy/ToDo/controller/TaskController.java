package javaDaddy.ToDo.controller;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import javaDaddy.ToDo.mapper.TaskMapper;
import javaDaddy.ToDo.service.TaskService;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<?> addTask(@RequestBody Task task) {
        try {
            return new ResponseEntity<>(taskService.addTask(task), HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        try {
            return new ResponseEntity<>(taskMapper.toTaskDtoList(taskService.getAllTasks()), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") @NonNull Long id) {
        try {
            return new ResponseEntity<>(taskMapper.toTaskDto(taskService.getTaskById(id)), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id, @RequestBody Task task) {
        try {
            return new ResponseEntity<>(taskMapper.toTaskDto(taskService.updateTask(id, task)), HttpStatusCode.valueOf(201));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTasksByStatus(@PathVariable Status status) {
        try {
            return new ResponseEntity<>(taskMapper.toTaskDtoList(taskService.getTasksByStatus(status)), HttpStatusCode.valueOf(200));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Ошибка: " + e.getMessage());
        }
    }

    @GetMapping("/status/sorted")
    public ResponseEntity<?> getTasksSortedByStatus() {
        return ResponseEntity.ok(taskMapper.toTaskDtoList(taskService.getTasksSortedByStatusAsc()));
    }

    @GetMapping("/endtime/sorted")
    public ResponseEntity<?> getTasksSortedByEndTime() {
        return ResponseEntity.ok(taskMapper.toTaskDtoListEndTime(taskService.getTasksSortedByEndTimeAsc()));
    }
}
