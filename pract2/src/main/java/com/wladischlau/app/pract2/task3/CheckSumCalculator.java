package com.wladischlau.app.pract2.task3;

import java.nio.ByteBuffer;

public class CheckSumCalculator {

    public static byte[] calculate16BitCheckSum(byte[] bytes) {
        short sum = 0;

        for (byte b : bytes) {
            sum += (short) (b & 0xFF); // Make bytes unsigned
        }

        return ByteBuffer // Equivalent of new byte[] { (byte)(sum >> 8), (byte)sum }
                .allocate(Short.BYTES)
                .putShort(0, sum)
                .array();
    }

    public static String calculateString16BitCheckSum(byte[] bytes) {
        return new String(calculate16BitCheckSum(bytes));
    }
}
