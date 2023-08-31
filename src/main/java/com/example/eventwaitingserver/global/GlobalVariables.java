package com.example.eventwaitingserver.global;

import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
public class GlobalVariables {

    private Long eventMaxMember;
}
