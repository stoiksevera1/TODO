package javaDaddy.ToDo.service;

import jakarta.transaction.Transactional;
import javaDaddy.ToDo.exception.TaskNonFoundException;
import javaDaddy.ToDo.entity.Task;
import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;

    @Transactional
    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    @Transactional
    public List<Task> getAllTasks() {
        return taskRepository.findAll();
    }

    @Transactional
    public Task getTaskById(Long id) throws TaskNonFoundException {
        return taskRepository.findById(id)
                .orElseThrow(() -> new TaskNonFoundException("Задача не найдена!"));
    }

    @Transactional
    public Task updateTask(Long id, Task taskUpdate) throws TaskNonFoundException {
        Task existingTask = taskRepository.findById(id)
                .orElseThrow(() -> new TaskNonFoundException("Задача не найдена"));
        existingTask.setName(taskUpdate.getName());
        existingTask.setDescription(taskUpdate.getDescription());
        existingTask.setStartTime(taskUpdate.getStartTime());
        existingTask.setEndTime(taskUpdate.getEndTime());
        existingTask.setStatus(taskUpdate.getStatus());
        return existingTask;
    }

    @Transactional
    public void deleteTask(Long id) throws TaskNonFoundException {
        if (!taskRepository.existsById(id)) {
            throw new TaskNonFoundException("Задача не найдена");
        }
        taskRepository.deleteById(id);
    }

    @Transactional
    public List<Task> getTasksByStatus(Status status) throws TaskNonFoundException {
        List<Task> tasks = taskRepository.findByStatus(status);
        if (tasks.isEmpty()) {
            throw new TaskNonFoundException("Задачи с таким статусом не найдены");
        }
        return tasks;
    }

    @Transactional
    public List<Task> getTasksSortedByStatusAsc() {
        return taskRepository.findAllByOrderByStatusAsc();
    }

    @Transactional
    public List<Task> getTasksSortedByEndTimeAsc() {
        return taskRepository.findAllByOrderByEndTimeAsc();
    }
}
