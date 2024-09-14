package com.wladischlau.app.utils;

import java.util.Scanner;

public class InputUtils {

    private static final Scanner SCANNER_SYSTEM_IN = new Scanner(System.in);

    public static String tryReadLine(String message) {
        System.out.println(message);
        return SCANNER_SYSTEM_IN.nextLine();
    }
}
