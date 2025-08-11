package javaDaddy.ToDo.service;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import javaDaddy.ToDo.exception.TaskNotFoundException;
import javaDaddy.ToDo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;


    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена!"));
    }

    @Transactional
    public Task updateTask(Long id, Task taskUpdate) {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNotFoundException("Задача не найдена"));
        existingTask.setName(taskUpdate.getName());
        existingTask.setDescription(taskUpdate.getDescription());
        existingTask.setStartTime(taskUpdate.getStartTime());
        existingTask.setEndTime(taskUpdate.getEndTime());
        existingTask.setStatus(taskUpdate.getStatus());
        return existingTask;
    }

    @Transactional
    public void deleteTask(Long id) {
        int affected = taskRepository.deleteByIdReturningCount(id);
        if (affected == 0) {
            throw new TaskNotFoundException("Задача не найдена");
        }
    }

    public List<Task> getTasksByStatus(Status status) {
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            throw new TaskNotFoundException("Задачи с таким статусом не найдены");
        }
        return tasks;
    }

    public List<Task> getTasksSortedByStatusAsc() {
        return taskRepository.findAllByOrderByStatusAsc();
    }

    public List<Task> getTasksSortedByEndTimeAsc() {
        return taskRepository.findAllByOrderByEndTimeAsc();
    }
}
