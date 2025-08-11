package javaDaddy.ToDo.controller;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import javaDaddy.ToDo.mapper.TaskMapper;
import javaDaddy.ToDo.service.TaskService;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/tasks")
@RequiredArgsConstructor
@Validated
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    @PostMapping
    public ResponseEntity<?> addTask(@Valid @RequestBody Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskMapper.toTaskDto(taskService.addTask(task)));
    }

    @GetMapping
    public ResponseEntity<?> getAllTasks() {
        return ResponseEntity
                .ok(taskMapper.toTaskDtoList(taskService.getAllTasks()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getTaskById(@PathVariable("id") @NonNull Long id) {
        return ResponseEntity
                .ok(taskMapper.toTaskDto(taskService.getTaskById(id)));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateTask(@PathVariable("id") Long id,
                                        @Valid @RequestBody Task task) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(taskMapper.toTaskDto(taskService.updateTask(id, task)));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTask(@PathVariable @NonNull Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/status/{status}")
    public ResponseEntity<?> getTasksByStatus(@PathVariable String status) {
        Status enumStatus = Status.valueOf(status.toUpperCase());
        return ResponseEntity.ok(
                taskMapper.toTaskDtoList(taskService.getTasksByStatus(enumStatus))
        );
    }

    @GetMapping("/status/sorted")
    public ResponseEntity<?> getTasksSortedByStatus() {
        return ResponseEntity
                .ok(taskMapper.toTaskDtoList(taskService.getTasksSortedByStatusAsc()));
    }

    @GetMapping("/endtime/sorted")
    public ResponseEntity<?> getTasksSortedByEndTime() {
        return ResponseEntity
                .ok(taskMapper.toTaskDtoListEndTime(taskService.getTasksSortedByEndTimeAsc()));
    }
}

