package com.wladischlau.app.pract1;

import com.wladischlau.app.pract1.task1.ListUtils;
import com.wladischlau.app.test.TimingExtension;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(TimingExtension.class)
public class ListUtilsTest {

    private final Random random = new Random(18490);
    private final ListUtils listUtils = new ListUtils();

    @Test
    public void givenListSize10000_whenGetMaxValue_returnMaxValueOfList() {
        List<Integer> list = getRandomGeneratedList(10000);

        int maxValue = listUtils.getMaxValue(list);

        assertTrue(maxValue > 0);
    }

    @Test
    public void givenListSize10000_whenGetMaxValueMultithreading_returnMaxValueOfList() {
        List<Integer> list = getRandomGeneratedList(10000);

        int maxValue = listUtils.getMaxValueMultithreading(list);

        assertTrue(maxValue > 0);
    }

    @Test
    public void givenListSize10000_whenGetMaxValueForkJoin_returnMaxValueOfList() {
        List<Integer> list = getRandomGeneratedList(10000);

        int maxValue = listUtils.getMaxValueForkJoin(list);

        assertTrue(maxValue > 0);
    }

    private List<Integer> getRandomGeneratedList(int size) {
        return random.ints(size).boxed().toList();
    }
}
