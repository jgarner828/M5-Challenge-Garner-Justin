package com.garnerju.invoiceservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class InvoiceTest {

    @Test
    public void testThatYouCanCreateNewInvoiceWithArgs() {

        Invoice test1 = new Invoice(0,"Justin","Peachtree St","Atlanta","GA","30050","shirts",4, BigDecimal.valueOf(10.00),1,BigDecimal.valueOf(10.00),BigDecimal.valueOf(10.00),BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00));
        Invoice test2 = new Invoice(0,"Justin","Peachtree St","Atlanta","GA","30050","shirts",4, BigDecimal.valueOf(10.00),1,BigDecimal.valueOf(10.00),BigDecimal.valueOf(10.00),BigDecimal.valueOf(10.00), BigDecimal.valueOf(10.00));

        assertEquals(test1, test2);

    }
}