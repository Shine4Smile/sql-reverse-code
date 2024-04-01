package com.simple;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.simple.mapper")
public class SqlReverseCodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(SqlReverseCodeApplication.class, args);
    }

}
