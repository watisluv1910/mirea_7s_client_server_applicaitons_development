package com.wladischlau.app.pract2.task2;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCopier {

    public static void copyWithFileStream(String source, String destination) {
        try (var inputStream = new FileInputStream(source);
             var outputStream = new FileOutputStream(destination)) {

            var buffer = new byte[4096];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWithFileChannel(String source, String destination) {
        try (var srcStream = new FileInputStream(source);
             var dstStream = new FileOutputStream(destination)) {
            FileChannel srcChannel = srcStream.getChannel();
            FileChannel dstChannel = dstStream.getChannel();

            long transferred = 0;
            while (transferred < srcChannel.size()) {
                transferred += dstChannel.transferFrom(srcChannel, transferred, srcChannel.size());
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWithApacheCommons(File source, File destination) {
        try {
            FileUtils.copyFile(source, destination); // Uses Files.copy() under the hood
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void copyWithFilesClass(Path source, Path destination) {
        try {
            Files.copy(source, destination);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
