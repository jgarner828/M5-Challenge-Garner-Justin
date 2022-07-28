package com.garnerju.invoiceservice.controller;

import com.garnerju.invoiceservice.util.feign.CatalogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    private CatalogClient catalogClient;

    InvoiceController(CatalogClient catalogClient) {this.catalogClient = catalogClient;}

    @GetMapping("/invoices")
    public String getInvoice() { return "Working!";}

}
