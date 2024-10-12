package com.wladischlau.app.pract1.task2;

import com.wladischlau.app.utils.InputUtils;

import java.util.Objects;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;

public class AsyncInputProcessor {

    public <T, R> void process(Function<T, R> task, Function<String, T> inputProcessor) {
        try (ExecutorService executor = Executors.newFixedThreadPool(5)) {
            while (true) {
                String input = InputUtils.tryReadLine("Enter the value to process (or 'exit' to close the program): ");

                if (Objects.equals("exit", input)) {
                    break;
                }

                Future<R> future = executor.submit(() -> {
                    T value = inputProcessor.apply(input);
                    return task.apply(value);
                });

                R result = tryGetResult(future);
                System.out.println("Initial value: " + input + " Result: " + result);
            }
        }
    }

    private <R> R tryGetResult(Future<R> future) {
        try {
            return future.get();
        } catch (ExecutionException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }
}
