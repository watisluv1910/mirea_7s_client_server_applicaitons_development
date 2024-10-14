package com.wladischlau.app.pract4_server;

import io.rsocket.frame.decoder.PayloadDecoder;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.context.ImportTestcontainers;
import org.springframework.http.codec.json.Jackson2JsonDecoder;
import org.springframework.http.codec.json.Jackson2JsonEncoder;
import org.springframework.messaging.rsocket.RSocketRequester;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.util.MimeTypeUtils;
import org.testcontainers.containers.PostgreSQLContainer;
import reactor.util.retry.Retry;

import java.time.Duration;

import static java.lang.String.format;

@ImportTestcontainers
@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public abstract class BasicDealershipControllerTest {

    @Value("${spring.rsocket.server.port}")
    private int rSocketPort;

    protected RSocketRequester requester;
    static PostgreSQLContainer<?> postgresContainer;

    static {
        postgresContainer = new PostgreSQLContainer<>("postgres:17-alpine")
                .withDatabaseName("dealership")
                .withUsername("admin")
                .withPassword("admin")
                .withEnv("POSTGRES_INITDB_ARGS", "--auth-host=scram-sha-256")
                .withEnv("PGDATA", "/var/lib/postgresql/data/pgdata");

        postgresContainer.start();
    }

    @BeforeEach
    public void setUp() {
        requester = RSocketRequester.builder()
                .rsocketStrategies(builder -> builder.decoder(new Jackson2JsonDecoder()))
                .rsocketStrategies(builder -> builder.encoder(new Jackson2JsonEncoder()))
                .rsocketConnector(connector -> connector
                        .payloadDecoder(PayloadDecoder.ZERO_COPY)
                        .reconnect(Retry.fixedDelay(2, Duration.ofSeconds(2))))
                .dataMimeType(MimeTypeUtils.APPLICATION_JSON)
                .tcp("localhost", rSocketPort);
    }

    @AfterEach
    public void tearDown() {
        requester.dispose();
    }

    @DynamicPropertySource
    private static void properties(DynamicPropertyRegistry registry) {
        registry.add("spring.flyway.url", postgresContainer::getJdbcUrl);
        registry.add("spring.flyway.user", postgresContainer::getUsername);
        registry.add("spring.flyway.password", postgresContainer::getPassword);

        registry.add("spring.r2dbc.url", () -> format("r2dbc:postgresql://%s:%d/%s",
                                                      postgresContainer.getHost(),
                                                      postgresContainer.getFirstMappedPort(),
                                                      postgresContainer.getDatabaseName()));
        registry.add("spring.r2dbc.username", postgresContainer::getUsername);
        registry.add("spring.r2dbc.password", postgresContainer::getPassword);
    }
}
