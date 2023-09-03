package com.example.eventwaitingserver.service;

import com.example.eventwaitingserver.config.KafkaProducer;
import com.example.eventwaitingserver.dto.ResponseMessageDto;
import com.example.eventwaitingserver.global.GlobalVariables;
import com.example.eventwaitingserver.repository.WaitingRepository;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class WaitingService {
    private final GlobalVariables globalVariables;
    private final WaitingRepository waitingRepository;
    private final KafkaProducer kafkaProducer;
    private final RedissonClient redisson;
    public WaitingService(GlobalVariables globalVariables,
                          WaitingRepository waitingRepository,
                          KafkaProducer kafkaProducer,
                          RedissonClient redissonClient) {
        this.globalVariables = globalVariables;
        this.waitingRepository = waitingRepository;
        this.kafkaProducer = kafkaProducer;
        this.redisson = redissonClient;
    }

    public ResponseMessageDto addWaiting(Long eventId, Long memberId) {
        RLock lock = redisson.getLock("waiting-lock");

        try {
            boolean isLocked = lock.tryLock(5000, 1000, TimeUnit.MILLISECONDS);

            if (!isLocked) {
                throw new Exception("대시 시간 초과");
            }

            // 크리티컬 섹션 - 락으로 보호되어야 하는 코드
            waitingValidate(eventId, memberId);

            waitingRepository.add(eventId, memberId);
            kafkaProducer.send(eventId, memberId);

            lock.unlock();

            return new ResponseMessageDto("Success");
        } catch (Exception e) {
            return new ResponseMessageDto(e.getMessage());
        }
    }

    public void waitingValidate(Long eventId, Long memberId) throws Exception {
        Long maxMember = globalVariables.getEventMaxMember();
        Long currentSize = waitingRepository.size(eventId);

        if (currentSize >= maxMember) {
            throw new Exception("정원이 초과되어 이벤트에 참여할 수 없습니다.");
        }

        if (waitingRepository.isMember(eventId, memberId)) {
            throw new Exception("이미 참여한 이벤트 입니다.");
        }
    }
}
