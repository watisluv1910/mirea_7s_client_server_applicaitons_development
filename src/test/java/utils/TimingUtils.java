package utils;

import lombok.SneakyThrows;

import java.util.concurrent.Callable;
import java.util.logging.Logger;

public class TimingUtils {

    @SneakyThrows
    public static void logExecutionTime(Runnable callable, Logger log) {
        long startTime = System.currentTimeMillis();
        callable.run();
        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("Inner method took " + elapsedTime + " ms.");
    }

    @SneakyThrows
    public static <T> T logExecutionTime(Callable<T> callable, Logger log) {
        long startTime = System.currentTimeMillis();
        T result = callable.call();
        long elapsedTime = System.currentTimeMillis() - startTime;

        log.info("Inner method took " + elapsedTime + " ms.");
        return result;
    }
}
