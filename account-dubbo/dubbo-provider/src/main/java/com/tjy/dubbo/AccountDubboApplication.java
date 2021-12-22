package com.tjy.dubbo;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@EnableDubbo
public class AccountDubboApplication {
    public static void main(String[] args) {
        SpringApplication.run(AccountDubboApplication.class, args);
    }
}