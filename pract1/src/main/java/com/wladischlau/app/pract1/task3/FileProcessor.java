package com.wladischlau.app.pract1.task3;

import com.wladischlau.app.utils.ThreadUtils;

import java.util.concurrent.BlockingQueue;

public class FileProcessor implements Runnable {

    private final BlockingQueue<File> queue;
    private final FileType fileType;

    public FileProcessor(BlockingQueue<File> queue, FileType fileType) {
        this.queue = queue;
        this.fileType = fileType;
    }

    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                File file = queue.take();
                if (file.getType().equals(fileType)) {
                    processFile(file);
                }
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static void processFile(File file) {
        long processingTime = file.getSize() * 7L;
        ThreadUtils.sleep(processingTime);

        System.out.println("Обработан файл типа " + file.getType() +
                           " с размером " + file.getSize() +
                           ". Время обработки: " + processingTime + " мс.");
    }
}
