package com.garnerju.catalogservice.models;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleTest {
    @Test
    public void testingThatYouCanCreateNewConsoleWithArgs() {

        Console test1 =
                new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);
        Console test2 =
                new Console(Long.valueOf(1), "64", "Nintendo", "64kb", "AMD64",  BigDecimal.valueOf(19.99), 10);

        assertEquals(test1, test2);
    }

}