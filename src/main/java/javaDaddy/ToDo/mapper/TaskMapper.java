package javaDaddy.ToDo.mapper;


import javaDaddy.ToDo.dto.TaskDto;
import javaDaddy.ToDo.dto.TaskDtoTime;
import javaDaddy.ToDo.entity.Task;
import org.mapstruct.Mapper;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface TaskMapper {
    TaskDto toTaskDto(Task task);
    List<TaskDto> toTaskDtoList(List<Task> tasks);
    List<TaskDtoTime> toTaskDtoListEndTime(List<Task> tasks);
}
