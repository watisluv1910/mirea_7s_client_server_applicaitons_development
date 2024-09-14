package pract1;

import com.wladischlau.app.pract1.task3.File;
import com.wladischlau.app.pract1.task3.FileGenerator;
import com.wladischlau.app.pract1.task3.FileProcessor;
import com.wladischlau.app.pract1.task3.FileType;
import com.wladischlau.app.utils.ThreadUtils;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TimingExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

@Log
@ExtendWith(TimingExtension.class)
public class FileProcessingTest {

    private BlockingDeque<File> queue;
    private ByteArrayOutputStream testOut;

    @BeforeEach
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        queue = new LinkedBlockingDeque<>(5);
    }

    @Test
    public void givenFileTypes_whenGeneratingFile_thenProcessFiles() {
        Thread jsonProcessorThread = new Thread(new FileProcessor(queue, FileType.JSON));
        Thread xmlProcessorThread = new Thread(new FileProcessor(queue, FileType.XML));
        Thread xlsProcessorThread = new Thread(new FileProcessor(queue, FileType.XLS));

        Thread generatorThread = new Thread(new FileGenerator(queue));

        generatorThread.start();
        jsonProcessorThread.start();
        xmlProcessorThread.start();
        xlsProcessorThread.start();

        ThreadUtils.sleep(10_000);

        String output = testOut.toString();
        log.info(output);
        Assertions.assertNotNull(output);
    }
}
