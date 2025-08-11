package javaDaddy.ToDo.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "tasks")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "Название задачи не должно быть пустым")
    private String name;

    private String description;

    @NotNull(message = "Дата начала обязательна")
    @FutureOrPresent(message = "Дата начала не может быть в прошлом")
    private LocalDate startTime;

    @NotNull(message = "Дата окончания обязательна")
    @FutureOrPresent(message = "Дата окончания должна быть настоящей или будущей")
    private LocalDate endTime;

    @NotNull(message = "Статус обязателен")
    @Enumerated(EnumType.STRING)
    private Status status;
}

