package pract2;

import com.wladischlau.app.pract2.task3.CheckSumCalculator;
import lombok.extern.java.Log;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import utils.TimingExtension;

@Log
@ExtendWith(TimingExtension.class)
public class CheckSumCalculatorTest {

    @Test
    public void givenJsonString_whenCalculate16BitCheckSum_thenReturnCorrect() {
        String json = """
                {
                    "tessadt": 5,
                    "cheeeeck": "check"
                }
                """;

        byte[] sum = CheckSumCalculator.calculate16BitCheckSum(json.getBytes());

        logByteArray(sum);
        Assertions.assertNotNull(sum);
    }

    private static void logByteArray(byte[] bytes) {
        StringBuilder message = new StringBuilder();
        for (byte b : bytes) {
            message.append(String.format("0x%02X ", b));
        }
        log.info(message.toString());
    }
}
