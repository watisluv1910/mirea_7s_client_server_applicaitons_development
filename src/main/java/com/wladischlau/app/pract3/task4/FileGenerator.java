package com.wladischlau.app.pract3.task4;

import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

public class FileGenerator {
    private static final Random random = new Random();

    public static Flowable<File> generateFiles() {
        return Flowable.interval(random.nextInt(901) + 100, TimeUnit.MILLISECONDS)
                .map(tick -> new File(getRandomFileType(), random.nextInt(91) + 10))
                .onBackpressureBuffer(5);
    }

    private static File.FileType getRandomFileType() {
        return File.FileType.values()[random.nextInt(File.FileType.values().length)];
    }
}