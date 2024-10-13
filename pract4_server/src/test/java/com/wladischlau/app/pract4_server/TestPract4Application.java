package com.wladischlau.app.pract4_server;

import org.springframework.boot.SpringApplication;

public class TestPract4Application {

    public static void main(String[] args) {
        SpringApplication.from(Pract4ServerApplication::main).with(TestcontainersConfiguration.class).run(args);
    }
}
