package com.wladischlau.app.pract1.task3;

import com.wladischlau.app.utils.ThreadUtils;

import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadLocalRandom;

public class FileGenerator implements Runnable {

    private final BlockingQueue<File> queue;

    public FileGenerator(BlockingQueue<File> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        Random rand = ThreadLocalRandom.current();

        while (!Thread.currentThread().isInterrupted()) {
            try {
                ThreadUtils.sleep(rand.nextInt(901) + 100);

                FileType fileType = getRandomFileType();
                int fileSize = getRandomFileSize();

                File file = new File(fileType, fileSize);
                queue.put(file);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }
        }
    }

    private static int getRandomFileSize() {
        return ThreadLocalRandom.current().nextInt(91) + 10;
    }

    private FileType getRandomFileType() {
        int idx = ThreadLocalRandom.current().nextInt(FileType.values().length);
        return FileType.values()[idx];
    }
}
