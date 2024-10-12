package com.wladischlau.app.pract3;

import com.wladischlau.app.pract3.task3.UserFriendDataGenerator;
import com.wladischlau.app.pract3.task3.UserFriendService;
import com.wladischlau.app.test.TestConsoleOutput;
import io.reactivex.rxjava3.core.Observable;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Random;

public class UserFriendServiceTest {

    private static final Random random = new Random();

    private TestConsoleOutput consoleOutput;

    private UserFriendService userFriendService;

    @BeforeEach
    void setUp() {
        var dataGenerator = new UserFriendDataGenerator();
        var userFriends = dataGenerator.getUserFriends();
        userFriendService = new UserFriendService(userFriends);
    }

    @BeforeEach
    void enableTestConsoleOutput() {
        consoleOutput = new TestConsoleOutput();
        consoleOutput.start();
    }

    @AfterEach
    void disableTestConsoleOutput() {
        consoleOutput.stop();
    }

    @Test
    public void givenRandomUserIds_whenFormingFriendsStream_thenSuccess() {
        // Given
        var userIds = random.ints(10, 1, 101).boxed().toList();

        // When
        Observable.fromIterable(userIds)
                .flatMap(userId -> userFriendService.getFriends(userId))
                .subscribe(System.out::println);

        // Then
        consoleOutput.assertNotNull();
    }
}
