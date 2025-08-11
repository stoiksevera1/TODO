package javaDaddy.ToDo;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import javaDaddy.ToDo.exception.TaskNotFoundException;
import javaDaddy.ToDo.repository.TaskRepository;
import javaDaddy.ToDo.service.TaskService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TaskServiceTest {

    @Mock
    private TaskRepository taskRepository;

    @InjectMocks
    private TaskService taskService;

    private Task task;

    @BeforeEach
    void setUp() {
        task = Task.builder()
                .id(1L)
                .name("Test Task")
                .description("Test Desc")
                .startTime(LocalDate.of(2025, 1, 1))
                .endTime(LocalDate.of(2025, 1, 10))
                .status(Status.IN_PROGRESS)
                .build();
    }

    @Test
    void addTask_ShouldSaveTask() {
        when(taskRepository.save(task)).thenReturn(task);

        Task saved = taskService.addTask(task);

        assertThat(saved).isEqualTo(task);
        verify(taskRepository).save(task);
    }

    @Test
    void getAllTasks_ShouldReturnList() {
        when(taskRepository.findAll()).thenReturn(List.of(task));

        List<Task> result = taskService.getAllTasks();

        assertThat(result).hasSize(1).contains(task);
    }

    @Test
    void getTaskById_ShouldReturnTask_WhenExists() throws TaskNotFoundException {
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.getTaskById(1L);

        assertThat(result).isEqualTo(task);
    }

    @Test
    void getTaskById_ShouldThrow_WhenNotExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.getTaskById(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Задача не найдена!");
    }

    @Test
    void updateTask_ShouldUpdateFields() throws TaskNotFoundException {
        Task update = Task.builder()
                .name("Updated Name")
                .description("Updated Desc")
                .startTime(LocalDate.of(2025, 2, 1))
                .endTime(LocalDate.of(2025, 2, 10))
                .status(Status.DONE)
                .build();

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        Task result = taskService.updateTask(1L, update);

        assertThat(result.getName()).isEqualTo("Updated Name");
        assertThat(result.getStatus()).isEqualTo(Status.DONE);
    }

    @Test
    void updateTask_ShouldThrow_WhenNotExists() {
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> taskService.updateTask(1L, task))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void deleteTask_ShouldCallDelete_WhenExists() {
        when(taskRepository.deleteByIdReturningCount(1L)).thenReturn(1);

        taskService.deleteTask(1L);

        verify(taskRepository).deleteByIdReturningCount(1L);
    }

    @Test
    void deleteTask_ShouldThrowTaskNotFoundException_WhenNotExists() {
        when(taskRepository.deleteByIdReturningCount(1L)).thenReturn(0);

        assertThatThrownBy(() -> taskService.deleteTask(1L))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessageContaining("Задача не найдена");
    }

    @Test
    void getTasksByStatus_ShouldReturnTasks() throws TaskNotFoundException {
        when(taskRepository.findByStatus(Status.IN_PROGRESS)).thenReturn(List.of(task));

        List<Task> result = taskService.getTasksByStatus(Status.IN_PROGRESS);

        assertThat(result).hasSize(1);
    }

    @Test
    void getTasksByStatus_ShouldThrow_WhenEmpty() {
        when(taskRepository.findByStatus(Status.IN_PROGRESS)).thenReturn(Collections.emptyList());

        assertThatThrownBy(() -> taskService.getTasksByStatus(Status.IN_PROGRESS))
                .isInstanceOf(TaskNotFoundException.class);
    }

    @Test
    void getTasksSortedByStatusAsc_ShouldReturnSorted() {
        when(taskRepository.findAllByOrderByStatusAsc()).thenReturn(Collections.singletonList(task));

        List<Task> result = taskService.getTasksSortedByStatusAsc();

        assertThat(result).hasSize(1);
        verify(taskRepository).findAllByOrderByStatusAsc();
    }

    @Test
    void getTasksSortedByEndTimeAsc_ShouldReturnSorted() {
        when(taskRepository.findAllByOrderByEndTimeAsc()).thenReturn(List.of(task));

        List<Task> result = taskService.getTasksSortedByEndTimeAsc();

        assertThat(result).containsExactly(task);
        verify(taskRepository).findAllByOrderByEndTimeAsc();
    }
}
