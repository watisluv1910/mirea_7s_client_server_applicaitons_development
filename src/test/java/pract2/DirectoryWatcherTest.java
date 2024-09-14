package pract2;

import com.wladischlau.app.pract2.task4.DirectoryWatcher;
import lombok.extern.java.Log;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import java.nio.file.Path;
import java.nio.file.WatchEvent;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

import static java.nio.file.StandardWatchEventKinds.ENTRY_CREATE;
import static java.nio.file.StandardWatchEventKinds.ENTRY_MODIFY;
import static java.nio.file.StandardWatchEventKinds.ENTRY_DELETE;

@Log
public class DirectoryWatcherTest {

    private static Map<WatchEvent.Kind<Path>, List<Consumer<WatchEvent<?>>>> handlers;

    @BeforeAll
    public static void init() {
        handlers = new HashMap<>() {{
            put(ENTRY_CREATE, Collections.singletonList(DirectoryWatcherTest::onCreateEvent));
            put(ENTRY_MODIFY, Collections.singletonList(DirectoryWatcherTest::onModifyEvent));
            put(ENTRY_DELETE, Collections.singletonList(DirectoryWatcherTest::onDeleteEvent));
        }};
    }

    @Test
    public void givenDirectoryPath_whenFileCreated_thenPerformOnCreateEvent() throws Exception {
        try (DirectoryWatcher watcher = new DirectoryWatcher("/home/wladischlau/IdeaProjects/mirea/7s/csad_pract1/src/test/resources", handlers)) {
            watcher.watch(); // TODO: Make solid tests
        }
    }

    public static void onCreateEvent(WatchEvent<?> event) {
        log.info("Event kind: ON_CREATE. File affected: " + event.context());
    }

    public static void onModifyEvent(WatchEvent<?> event) {
        log.info("Event kind: ON_MODIFY. File affected: " + event.context());
    }

    public static void onDeleteEvent(WatchEvent<?> event) {
        log.info("Event kind: ON_DELETE. File affected: " + event.context());
    }
}
