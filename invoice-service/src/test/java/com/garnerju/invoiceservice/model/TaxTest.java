package com.garnerju.invoiceservice.model;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.Assert.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class TaxTest {

    @Test
    public void testThatYouCanCreateNewSalesTaxRateWithArgs() {
        Tax test1 =
                new Tax( "GA", BigDecimal.valueOf(0.55));

        Tax test2 =
                new Tax( "GA", BigDecimal.valueOf(0.55));

        assertEquals(test1,test2);

    }
}