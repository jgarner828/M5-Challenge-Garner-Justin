package com.garnerju.invoiceservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ProcessingFeeTest {

    @Test
    public void testThatYouCanCreateNewProcessingFeeWithArgs() {

        ProcessingFee test1 = new ProcessingFee( "Consoles", BigDecimal.valueOf(15.99));
        ProcessingFee test2 = new ProcessingFee( "Consoles", BigDecimal.valueOf(15.99));

        assertEquals(test1, test2);
    }
}