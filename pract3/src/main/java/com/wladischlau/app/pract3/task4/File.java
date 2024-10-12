package com.wladischlau.app.pract3.task4;

public record File(FileType fileType, int size) {

    public enum FileType {
        XML,
        JSON,
        XLS
    }
}
