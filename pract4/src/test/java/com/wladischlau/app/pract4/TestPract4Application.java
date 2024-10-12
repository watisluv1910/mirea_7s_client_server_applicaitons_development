package com.wladischlau.app.pract4;

import org.springframework.boot.SpringApplication;

public class TestPract4Application {

    public static void main(String[] args) {
        SpringApplication.from(Pract4Application::main).with(TestcontainersConfiguration.class).run(args);
    }
}
