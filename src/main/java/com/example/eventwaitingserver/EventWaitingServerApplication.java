package com.example.eventwaitingserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class EventWaitingServerApplication {

    public static void main(String[] args) {
        SpringApplication.run(EventWaitingServerApplication.class, args);
    }

}
