package com.kawa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.kawa.dao")
public class MobilePhoneApplication_80 {

    public static void main(String[] args) {
        SpringApplication.run(MobilePhoneApplication_80.class, args);
    }
}