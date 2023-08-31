package com.example.eventwaitingserver.dto;

import lombok.Getter;

@Getter
public class EventInfoDto {
    private Long eventId;
    private Long maxMember;

    public EventInfoDto(Long eventId, Long maxMember) {
        this.eventId = eventId;
        this.maxMember = maxMember;
    }
}
