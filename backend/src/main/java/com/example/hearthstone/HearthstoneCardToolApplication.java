package com.example.hearthstone;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@MapperScan("com.example.hearthstone.mapper")
@EnableCaching
public class HearthstoneCardToolApplication {

    public static void main(String[] args) {
        SpringApplication.run(HearthstoneCardToolApplication.class, args);
    }
}
