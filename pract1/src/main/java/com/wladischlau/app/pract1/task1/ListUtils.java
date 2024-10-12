package com.wladischlau.app.pract1.task1;

import com.google.common.collect.Lists;
import com.wladischlau.app.utils.ThreadUtils;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.Future;
import java.util.concurrent.RecursiveTask;

public class ListUtils {

    public int getMaxValue(@NotEmpty List<Integer> list) {
        return list
                .stream()
                .max((first, second) -> {
                    ThreadUtils.sleep(1L);
                    return first.compareTo(second);
                })
                .orElseThrow(() -> new RuntimeException("No value found"));
    }

    public int getMaxValueMultithreading(@NotEmpty List<Integer> list) {
        int numberOfThreads = Runtime.getRuntime().availableProcessors();

        List<Callable<Integer>> tasks = Lists.partition(list, numberOfThreads)
                .stream()
                .<Callable<Integer>>map(e -> () -> getMaxValue(e))
                .toList();

        int result = Integer.MIN_VALUE;

        try (ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads)) {
            List<Future<Integer>> futures = executor.invokeAll(tasks);

            for (Future<Integer> future : futures) {
                Integer partialMax = future.get();
                ThreadUtils.sleep(1L);
                if (partialMax > result) {
                    result = partialMax;
                }
            }
        } catch (InterruptedException | ExecutionException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }

        return result;
    }

    public int getMaxValueForkJoin(@NotEmpty List<Integer> list) {
        try (ForkJoinPool pool = new ForkJoinPool()) {
            MaxFinderTask task = new MaxFinderTask(list, 0, list.size());
            return pool.invoke(task);
        }
    }

    @AllArgsConstructor
    private class MaxFinderTask extends RecursiveTask<Integer> {

        private List<Integer> list;
        private int start;
        private int end;

        @Override
        protected Integer compute() {
            if (end - start <= 1000) {
                return getMaxValue(new ArrayList<>(list.subList(start, end)));
            }

            int middle = start + (end - start) / 2;

            MaxFinderTask leftTask = new MaxFinderTask(list, start, middle);
            MaxFinderTask rightTask = new MaxFinderTask(list, middle, end);

            leftTask.fork();
            int rightResult = rightTask.compute();
            int leftResult = leftTask.join();

            ThreadUtils.sleep(1L);

            return Math.max(leftResult, rightResult);
        }
    }
}
