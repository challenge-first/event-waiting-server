package com.example.eventwaitingserver.service;

import com.example.eventwaitingserver.config.KafkaProducer;
import com.example.eventwaitingserver.global.GlobalVariables;
import com.example.eventwaitingserver.repository.EventInfoRepository;
import com.example.eventwaitingserver.repository.WaitingRepository;
import org.springframework.stereotype.Service;

@Service
public class WaitingService {
    private final GlobalVariables globalVariables;
    private final WaitingRepository waitingRepository;
    private final KafkaProducer kafkaProducer;

    public WaitingService(GlobalVariables globalVariables, WaitingRepository waitingRepository, KafkaProducer kafkaProducer) {
        this.globalVariables = globalVariables;
        this.waitingRepository = waitingRepository;
        this.kafkaProducer = kafkaProducer;
    }

    public void addWaiting(Long eventId, Long memberId) {
        Long maxMember = globalVariables.getEventMaxMember();
        Long currentSize = waitingRepository.size(eventId);

        if (currentSize >= maxMember) {
            return;
        }

        waitingRepository.add(eventId, memberId);

        kafkaProducer.send(eventId, memberId);
    }
}
