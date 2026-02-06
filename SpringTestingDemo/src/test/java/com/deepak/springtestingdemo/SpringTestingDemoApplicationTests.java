package com.deepak.springtestingdemo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.*;

@SpringBootTest
class SpringTestingDemoApplicationTests {

    @Test
    void contextLoads() {
    }

    @Test
    void lambdaExpressions() {
        List numbers = Arrays.asList(1, 2, 3);
        assertTrue(numbers.stream()
                .mapToInt(Integer::intValue)
                .sum() > 5, () -> "Sum should be greater than 5");
    }

    @Test
    void groupAssertions() {
        int[] numbers = {0, 1, 2, 3, 4};
        assertAll("numbers",
                () -> assertEquals(numbers[0], 1),
                () -> assertEquals(numbers[3], 3),
                () -> assertEquals(numbers[4], 1)
        );
    }

    @Test
    void trueAssumption() {
        assumeTrue(5 > 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    void falseAssumption() {
        assumeFalse(5 < 1);
        assertEquals(5 + 2, 7);
    }

    @Test
    void assumptionThat() {
        String someString = "Just a string";
        assumingThat(
                someString.equals("Just a string"),
                () -> assertEquals(2 + 2, 4)
        );
    }

}
