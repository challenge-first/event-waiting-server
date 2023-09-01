package com.example.eventwaitingserver.controller;

import com.example.eventwaitingserver.dto.EventJoinRequestDto;
import com.example.eventwaitingserver.dto.ResponseMessageDto;
import com.example.eventwaitingserver.service.WaitingService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

@RestController
@RequestMapping("/events")
public class WaitingController {
    private final WaitingService waitingService;

    public WaitingController(WaitingService waitingService) {
        this.waitingService = waitingService;
    }

    @PostMapping("/{eventId}")
    public ResponseEntity<ResponseMessageDto> joinEvent(@PathVariable("eventId") Long eventId,
                                       @RequestBody EventJoinRequestDto requestDto) {
        ResponseMessageDto responseDto = waitingService.addWaiting(eventId, requestDto.getMemberId());

        return ResponseEntity.status(HttpStatus.OK.value()).body(responseDto);
    }
}
