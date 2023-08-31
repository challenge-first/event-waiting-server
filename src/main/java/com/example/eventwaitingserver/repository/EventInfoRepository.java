package com.example.eventwaitingserver.repository;

import com.example.eventwaitingserver.dto.EventInfoDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

@Repository
public class EventInfoRepository {

    private final RedisTemplate<String, String> redisTemplate;

    @Value("${redis.key.info}")
    private String info;

    public EventInfoRepository(RedisTemplate<String, String> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    public void addEvent(Long maxMember) {
        redisTemplate.opsForValue()
                .set(info, maxMember.toString());
    }

    public Long getMaxMember() {
        String maxMemberString = redisTemplate.opsForValue()
                .get(info);
        if (maxMemberString == null) {
            throw new IllegalArgumentException("이벤트가 없습니다.");
        }

        return Long.parseLong(maxMemberString);
    }
}
