package com.garnerju.invoiceservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TShirtTest {

    @Test
    public void testThatYouCanCreateNewShirtWithArgs() {
        TShirt newShirt = new TShirt(1, "small", "white", "shirt", BigDecimal.valueOf(10.00), 10);
        TShirt newShirt2 = new TShirt(1, "small", "white", "shirt", BigDecimal.valueOf(10.00), 10);

        assertEquals(newShirt,newShirt2);
    }

}