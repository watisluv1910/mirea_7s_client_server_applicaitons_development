package com.wladischlau.app.pract3;

import com.wladischlau.app.pract3.task4.File;
import com.wladischlau.app.pract3.task4.FileGenerator;
import com.wladischlau.app.pract3.task4.FileProcessor;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.subscribers.TestSubscriber;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class FileProcessorTest {

    private final List<FileProcessor> processors = new ArrayList<>();

    @BeforeEach
    public void setUp() {
        Arrays.stream(File.FileType.values()).map(FileProcessor::new).forEach(processors::add);
    }

    @AfterEach
    public void tearDown() {
        processors.clear();
    }

    @Test
    public void givenFileStream_whenProcessFiles_thenCorrectFilesProcessed() throws InterruptedException {
        // Given
        Flowable<File> fileStream = FileGenerator.generateFiles().take(10);

        // When
        TestSubscriber<File> testSubscriber = new TestSubscriber<>();

        processors.forEach(it -> {
            it.processFiles(fileStream);
            Flowable<File> processedFiles = fileStream.filter(file -> file.fileType() == it.supportedType());
            processedFiles.subscribe(testSubscriber);
        });

        testSubscriber.await(10, TimeUnit.SECONDS);

        // Then
        List<File> processedFiles = testSubscriber.values();
        assertTrue(processedFiles
                           .stream()
                           .allMatch(file -> processors
                                   .stream()
                                   .anyMatch(processor -> file.fileType() == processor.supportedType())
                           )
        );
    }
}
