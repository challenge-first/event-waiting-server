package com.example.eventwaitingserver.global;

import com.example.eventwaitingserver.repository.EventInfoRepository;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

@Component
public class CustomApplicationRunner implements ApplicationRunner {
    private final GlobalVariables globalVariables;
    private final EventInfoRepository eventInfoRepository;

    public CustomApplicationRunner(GlobalVariables globalVariables, EventInfoRepository eventInfoRepository) {
        this.globalVariables = globalVariables;
        this.eventInfoRepository = eventInfoRepository;
    }

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // 전역 변수 값 설정
        System.out.println("ApplicationRunner");
        globalVariables.setEventMaxMember(eventInfoRepository.getMaxMember());
    }
}
