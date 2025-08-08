package javaDaddy.ToDo.dto;

import java.time.LocalDate;

public record TaskDtoTime (
        String name,
        LocalDate endTime
){}
