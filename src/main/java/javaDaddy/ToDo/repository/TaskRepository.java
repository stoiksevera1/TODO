package javaDaddy.ToDo.repository;

import javaDaddy.ToDo.entity.Status;
import javaDaddy.ToDo.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository  extends JpaRepository<Task, Long> {
    List<Task> findByStatus(Status status);
    List<Task> findAllByOrderByStatusAsc();
    List<Task> findAllByOrderByEndTimeAsc();
    @Modifying
    @Query("delete from Task t where t.id = :id")
    int deleteByIdReturningCount(@Param("id") Long id);
}

