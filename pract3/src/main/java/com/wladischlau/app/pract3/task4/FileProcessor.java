package com.wladischlau.app.pract3.task4;

import io.reactivex.rxjava3.core.Flowable;

import java.util.concurrent.TimeUnit;

public record FileProcessor(File.FileType supportedType) {

    public void processFiles(Flowable<File> fileStream) {
        fileStream
                .filter(file -> file.fileType() == supportedType)
                .flatMap(file -> Flowable.just(file).delay(file.size() * 7L, TimeUnit.MILLISECONDS))
                .subscribe(file -> System.out.println("File processed: " + file));
    }
}