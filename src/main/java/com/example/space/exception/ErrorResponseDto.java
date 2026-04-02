package com.example.space.exception;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class ErrorResponseDto {
    private int status;
    private String message;
    private LocalDateTime timestamp;
    private List<String> details;

    public ErrorResponseDto(int status, String message, List<String> details) {
        this.status = status;
        this.message = message;
        this.details = details;
        this.timestamp = LocalDateTime.now();
    }
}