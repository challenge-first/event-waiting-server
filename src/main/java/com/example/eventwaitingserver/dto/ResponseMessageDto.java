package com.example.eventwaitingserver.dto;

import lombok.Getter;

@Getter
public class ResponseMessageDto {
    private String message;

    public ResponseMessageDto(String message) {
        this.message = message;
    }
}
