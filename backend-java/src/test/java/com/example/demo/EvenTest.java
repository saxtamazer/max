package com.example.demo;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Calendar;

public class EvenTest {
    @Test
    public void test() {
        Calendar c = Calendar.getInstance();
        System.out.println(c.get(Calendar.WEEK_OF_MONTH));
    }
}
