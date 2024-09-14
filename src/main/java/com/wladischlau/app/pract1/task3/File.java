package com.wladischlau.app.pract1.task3;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class File {

    private FileType type;
    private int size;
}
