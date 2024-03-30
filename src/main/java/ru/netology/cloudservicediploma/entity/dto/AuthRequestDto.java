package ru.netology.cloudservicediploma.entity.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record AuthRequestDto(
        @NotNull(message = "Значение login не может быть null")
        @NotBlank(message = "Значение login не может быть пустым")
        String login,

        @NotNull(message = "Значение password не может быть null")
        @NotBlank(message = "Значение password не может быть пустым")
        String password
) {
}
