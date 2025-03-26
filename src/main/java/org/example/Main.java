package org.example;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@ComponentScan(value = "org.example.controller, org.example.dao", )
public class Main {
    public static void main(String[] args) {
        SpringApplication.run(Main.class, args);
    }
}