package ru.netology.cloudservicediploma.entity.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public record FileDto(
        @JsonProperty("filename")
        String fileName,
        long size) {
}
