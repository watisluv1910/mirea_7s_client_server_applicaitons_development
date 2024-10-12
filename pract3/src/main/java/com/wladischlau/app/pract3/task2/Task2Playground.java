package com.wladischlau.app.pract3.task2;

import io.reactivex.rxjava3.core.Observable;

import java.util.Random;

public class Task2Playground {

    static Random random = new Random();

    public static void main(String[] args) {
        System.out.println("Subtask 1:");

        // Subtask 1
        Observable<Integer> subtask1Numbers = Observable.range(0, 1000)
                .map(_ -> random.nextInt(1001));

        subtask1Numbers
                .filter(num -> num > 500)
                .subscribe(System.out::println);

        System.out.println("Subtask 2:");

        // Subtask 2
        Observable<Integer> stream1 = Observable.range(0, 1000)
                .map(_ -> random.nextInt(10));

        Observable<Integer> stream2 = Observable.range(0, 1000)
                .map(_ -> random.nextInt(10));

        Observable.concat(stream1, stream2)
                .subscribe(System.out::println);

        System.out.println("Subtask 3:");

        // Subtask 3
        Observable<Integer> subtask3Numbers = Observable.range(0, 10)
                .map(_ -> random.nextInt(101));

        subtask3Numbers
                .take(5)
                .subscribe(System.out::println);
    }
}
