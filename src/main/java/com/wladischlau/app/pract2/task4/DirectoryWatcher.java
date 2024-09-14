package com.wladischlau.app.pract2.task4;

import lombok.Getter;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

@Getter
public class DirectoryWatcher implements AutoCloseable {

    private final Path path;
    private final WatchService watcher;

    private final ConcurrentHashMap<WatchEvent.Kind<Path>, List<Consumer<WatchEvent<?>>>> eventHandlers;

    public DirectoryWatcher(
            String path,
            Map<WatchEvent.Kind<Path>, List<Consumer<WatchEvent<?>>>> eventHandlers
    ) throws IOException {
        this.path = Paths.get(path);
        this.watcher = FileSystems.getDefault().newWatchService();
        this.eventHandlers = new ConcurrentHashMap<>(eventHandlers);

        this.path.register(watcher, ENTRY_CREATE, ENTRY_MODIFY, ENTRY_DELETE);
    }

    public void watch() {
        new Thread(this::watchBlocking).start();
    }

    public void watchBlocking() {
        try (ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor()) {
            WatchKey key;
            while ((key = watcher.take()) != null) {
                for (var event : key.pollEvents()) {
                    executor.submit(() -> handle(event));
                }
                key.reset();
            }
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException(e);
        }
    }

    private void handle(WatchEvent<?> event) {
        eventHandlers
                .get(event.kind())
                .parallelStream()
                .forEach(c -> c.accept(event));
    }

    @Override
    public void close() throws Exception {
        Thread.currentThread().interrupt();
        this.watcher.close();
    }
}
