package com.garnerju.invoiceservice.controller;

import com.garnerju.invoiceservice.model.Invoice;
import com.garnerju.invoiceservice.service.InvoiceService;
import com.garnerju.invoiceservice.util.feign.CatalogClient;
import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RefreshScope
public class InvoiceController {

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private InvoiceService invoiceService;

    InvoiceController(CatalogClient catalogClient) {this.catalogClient = catalogClient;}

    @GetMapping("/invoices")
    public List<Invoice> getInvoices() { return invoiceService.getInvoices(); }

    @GetMapping("/invoice/{id}")
    public Invoice  getInvoicesById(Long id) { return invoiceService.getInvoicesById(id);}

    @PostMapping("/invoice")
    public Invoice createInvoice(@RequestBody Invoice invoice) { return invoiceService.createInvoice(invoice); }

    @PutMapping("/invoice")
    public void updateInvoice(@RequestBody Invoice invoice) { invoiceService.updateInvoice(invoice); }

    @DeleteMapping("/invoice/{id}")
    public void deleteInvoice(@RequestBody Invoice invoice) { invoiceService.deleteInvoice(invoice); }
}
