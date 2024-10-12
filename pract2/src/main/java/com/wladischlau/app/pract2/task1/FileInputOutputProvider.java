package com.wladischlau.app.pract2.task1;

import lombok.extern.java.Log;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Log
public class FileInputOutputProvider {

    public static void writeLines(String fileName, List<String> lines) {
        Path path = Paths.get(fileName);
        try {
            Files.write(path, lines);
            log.info("Wrote " + lines.size() + " lines to " + path);
        } catch (IOException e) {
            log.severe("File write error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public static List<String> readLines(String fileName) {
        Path path = Paths.get(fileName);
        try {
            return Files.readAllLines(path);
        } catch (IOException e) {
            log.severe("File read error: " + e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
