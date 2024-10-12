package com.wladischlau.app.pract3.task3;

import lombok.Getter;

import java.util.Random;

public class UserFriendDataGenerator {

    private static final Random random = new Random();

    private static final int MAX_USERS = 100;
    private static final int MAX_FRIENDS_PER_USER = 10;

    @Getter
    private final UserFriend[] userFriends;

    public UserFriendDataGenerator() {
        userFriends = new UserFriend[MAX_USERS * MAX_FRIENDS_PER_USER];

        int index = 0;
        for (int userId = 1; userId <= MAX_USERS; userId++) {
            for (int i = 0; i < MAX_FRIENDS_PER_USER; i++) {
                int friendId = random.nextInt(MAX_USERS) + 1;
                userFriends[index++] = new UserFriend(userId, friendId);
            }
        }
    }
}