package com.kawa;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = "com.kawa.mapper")
public class PC_WebApplication_82 {

    public static void main(String[] args) {
        SpringApplication.run(PC_WebApplication_82.class, args);
    }

}
