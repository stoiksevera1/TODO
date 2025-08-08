package javaDaddy.ToDo.repository;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findAllByOrderByStatusAsc();
    List<Task> findAllByOrderByEndTimeAsc();
}
