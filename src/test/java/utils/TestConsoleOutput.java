package utils;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

public class TestConsoleOutput {

    private final ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
    private final PrintStream originalOut = System.out;

    public void start() {
        System.setOut(new PrintStream(outputStream));
    }

    public void stop() {
        System.setOut(originalOut);
    }

    public void assertNotNull() {
        if (outputStream.toString().isEmpty()) {
            throw new AssertionError("Expected non-empty output");
        }
    }

    public void assertOutputContains(String expectedOutput) {
        String consoleOutput = outputStream.toString();
        if (!consoleOutput.contains(expectedOutput)) {
            throw new AssertionError("Expected output to contain: " + expectedOutput + ", but got: " + consoleOutput);
        }
    }
}