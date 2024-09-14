package pract2;

import com.wladischlau.app.pract2.task1.FileInputOutputProvider;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;

public class FileInputOutputProviderTest {

    private static final String TEST_FILE_NAME = "test.txt";

    @AfterEach
    public void tearDown() throws IOException {
        Path path = Paths.get(TEST_FILE_NAME);
        Files.deleteIfExists(path);
    }

    @Test
    public void givenFilePathAndContent_whenWriting_thenWriteToFile() {
        ArrayList<String> lines = new ArrayList<>() {{
            add("First string");
            add("Second string");
            add("Third string");
        }};

        FileInputOutputProvider.writeLines(TEST_FILE_NAME, lines);

        Path path = Paths.get(TEST_FILE_NAME);
        Assertions.assertTrue(Files.exists(path));
    }

    @Test
    public void givenFilePathAndContent_whenReading_thenReadFromFile() {
        ArrayList<String> lines = new ArrayList<>() {{
            add("First string");
            add("Second string");
            add("Third string");
        }};

        FileInputOutputProvider.writeLines(TEST_FILE_NAME, lines);
        FileInputOutputProvider.readLines(TEST_FILE_NAME);

        Path path = Paths.get(TEST_FILE_NAME);
        Assertions.assertTrue(Files.exists(path));
        Assertions.assertEquals(lines, FileInputOutputProvider.readLines(TEST_FILE_NAME));
    }
}
