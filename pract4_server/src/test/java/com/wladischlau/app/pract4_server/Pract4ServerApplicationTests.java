package com.wladischlau.app.pract4_server;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@Import(TestcontainersConfiguration.class)
@SpringBootTest
class Pract4ServerApplicationTests {

    @Test
    void contextLoads() {
    }

}
