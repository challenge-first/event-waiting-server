package com.example.eventwaitingserver.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
public class ProduceDto {
    private Long eventId;
    private Long memberId;

    public ProduceDto(Long eventId, Long memberId) {
        this.eventId = eventId;
        this.memberId = memberId;
    }
}

