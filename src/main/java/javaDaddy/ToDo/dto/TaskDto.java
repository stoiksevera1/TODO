package javaDaddy.ToDo.dto;

import javaDaddy.ToDo.entity.Status;

import java.time.LocalDate;

public record TaskDto(
        String name,
        String description,
        LocalDate startTime,
        LocalDate endTime,
        Status status) {}

