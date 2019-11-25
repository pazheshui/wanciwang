package com;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;

@SpringBootApplication//这个注解表示这个类是启动类
public class mainApp  {
    public static void main(String[] args) throws Exception {
        SpringApplication.run(mainApp.class, args);
    }

}
