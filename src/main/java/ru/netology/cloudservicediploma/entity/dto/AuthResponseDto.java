package ru.netology.cloudservicediploma.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AuthResponseDto(
        @JsonProperty("auth-token")
        UUID authToken
) {
}
