package pract1;

import com.wladischlau.app.pract1.task2.AsyncInputProcessor;
import com.wladischlau.app.utils.ThreadUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TimingExtension;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TimingExtension.class)
public class AsyncInputProcessorTest {

    private ByteArrayOutputStream testOut;
    private AsyncInputProcessor asyncInputProcessor;

    @BeforeEach
    public void setUp() {
        testOut = new ByteArrayOutputStream();
        System.setOut(new PrintStream(testOut));

        asyncInputProcessor = new AsyncInputProcessor();
    }

    @Test
    public void givenUserInput_whenPerformingTask_returnFormattedInput() {
        String input = "4\n9\nexit\n";
        provideInput(input);

        asyncInputProcessor.process(this::calculateSquareWithDelay, Integer::valueOf);

        String output = testOut.toString();
        assertTrue(output.contains("Initial value: 4 Result: 16"));
        assertTrue(output.contains("Initial value: 9 Result: 81"));
    }

    public Integer calculateSquareWithDelay(Integer number) {
        int delayMultiplier = ThreadLocalRandom.current().nextInt(1, 6);
        ThreadUtils.sleep(1_000L * delayMultiplier);
        return calculateSquare(number);
    }

    public Integer calculateSquare(Integer number) {
        return number * number;
    }

    void provideInput(String data) {
        ByteArrayInputStream testIn = new ByteArrayInputStream(data.getBytes());
        System.setIn(testIn);
    }
}
