package com.example.eventwaitingserver.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.stereotype.Repository;

@Repository
public class WaitingRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${redis.key.event}")
    private String key;

    public WaitingRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void add(Long eventId, Long memberId) {
        SetOperations<String, String> opsForSet = redisTemplate.opsForSet();
        opsForSet.add(eventId.toString(), memberId.toString());
    }

    public Long size(Long eventId) {
        return redisTemplate.opsForSet().size(eventId.toString());
    }

    public boolean isMember(Long eventId, Long memberId) {
        return redisTemplate.opsForSet().isMember(eventId.toString(), memberId);
    }
}
