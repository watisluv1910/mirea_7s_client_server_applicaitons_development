package com.wladischlau.app.pract4_server;

import com.wladischlau.app.pract4.dto.Car;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Mono;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CarControllerTest extends BasicDealershipControllerTest {

    @Test
    @FlywayTest
    public void testGetCarById() {
        final Long testId = 1L;

        Mono<Car> res = requester.route("getCar")
                .data(testId)
                .retrieveMono(Car.class);

        assertNotNull(res.block());
        assertEquals(testId, res.block().id());
    }
}
