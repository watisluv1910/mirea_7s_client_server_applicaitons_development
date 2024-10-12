package com.wladischlau.app.pract3.task3;

import io.reactivex.rxjava3.core.Observable;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UserFriendService {

    private final UserFriend[] userFriends;

    public Observable<UserFriend> getFriends(int userId) {
        return Observable.fromArray(userFriends)
                .filter(userFriend -> userFriend.userId() == userId);
    }
}