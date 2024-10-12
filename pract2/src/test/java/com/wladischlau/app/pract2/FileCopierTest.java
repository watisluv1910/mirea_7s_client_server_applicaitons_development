package com.wladischlau.app.pract2;

import com.wladischlau.app.pract2.task2.FileCopier;
import com.wladischlau.app.test.TimingExtension;
import lombok.extern.java.Log;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.SeekableByteChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.EnumSet;

import static com.wladischlau.app.test.TimingUtils.logExecutionTime;
import static java.nio.file.StandardOpenOption.CREATE;
import static java.nio.file.StandardOpenOption.SPARSE;
import static java.nio.file.StandardOpenOption.WRITE;

@Log
@ExtendWith(TimingExtension.class)
public class FileCopierTest {

    private static final Path SRC_FILE_PATH = Paths.get("src/test/resources/test_src.txt");
    private static final Path DST_FILE_PATH = Paths.get("src/test/resources/test_dst.txt");
    private static final Integer TEST_FILE_SIZE_IN_BYTES = 1024 * 1024 * 1024; // 1 GB

    @BeforeAll
    public static void setUpBeforeClass() {
        initTestFile();
    }

    @AfterEach
    public void tearDown() {
        tryDeleteFile(DST_FILE_PATH);
    }

    @AfterAll
    public static void tearDownAfterClass() {
        tryDeleteFile(SRC_FILE_PATH);
    }

    @Test
    public void givenLargeFile_whenCopyWithFileStream_thenCorrect() throws IOException {
        logExecutionTime(
                () -> FileCopier.copyWithFileStream(
                        SRC_FILE_PATH.toString(),
                        DST_FILE_PATH.toString()
                ),
                log
        );

        Assertions.assertTrue(Files.exists(DST_FILE_PATH));
        Assertions.assertEquals(Files.readAllLines(SRC_FILE_PATH), Files.readAllLines(DST_FILE_PATH));
    }

    @Test
    public void givenLargeFile_whenCopyWithFileChannel_thenCorrect() throws IOException {
        logExecutionTime(
                () -> FileCopier.copyWithFileChannel(
                        SRC_FILE_PATH.toString(),
                        DST_FILE_PATH.toString()
                ),
                log
        );

        Assertions.assertTrue(Files.exists(DST_FILE_PATH));
        Assertions.assertEquals(Files.readAllLines(SRC_FILE_PATH), Files.readAllLines(DST_FILE_PATH));
    }

    @Test
    public void givenLargeFile_whenCopyWithApacheCommons_thenCorrect() throws IOException {
        logExecutionTime(
                () -> FileCopier.copyWithApacheCommons(
                        SRC_FILE_PATH.toFile(),
                        DST_FILE_PATH.toFile()
                ),
                log
        );

        Assertions.assertTrue(Files.exists(DST_FILE_PATH));
        Assertions.assertEquals(Files.readAllLines(SRC_FILE_PATH), Files.readAllLines(DST_FILE_PATH));
    }

    @Test
    public void givenLargeFile_whenCopyWithFilesClass_thenCorrect() throws IOException {
        logExecutionTime(
                () -> FileCopier.copyWithFilesClass(SRC_FILE_PATH, DST_FILE_PATH),
                log
        );

        Assertions.assertTrue(Files.exists(DST_FILE_PATH));
        Assertions.assertEquals(Files.readAllLines(SRC_FILE_PATH), Files.readAllLines(DST_FILE_PATH));
    }

    private static void initTestFile() {
        ByteBuffer byteBuffer = ByteBuffer.allocate(4).putInt(2); // Random value for comparison
        byteBuffer.rewind();

        // Open file for writing
        EnumSet<StandardOpenOption> options = EnumSet.of(WRITE, CREATE, SPARSE);

        try (SeekableByteChannel channel = Files.newByteChannel(SRC_FILE_PATH, options)) {
            // Set position to offset
            channel.position(TEST_FILE_SIZE_IN_BYTES);

            // Write a single byte at the position,
            // the OS should automatically extend the file to the desired number of bytes
            channel.write(byteBuffer);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void tryDeleteFile(Path filePath) {
        boolean deleted = filePath.toFile().delete();

        if (!deleted) {
            log.severe("Failed to delete file: " + SRC_FILE_PATH);
        }
    }
}
