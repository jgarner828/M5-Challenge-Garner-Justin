package com.garnerju.invoiceservice.controller;

import com.garnerju.invoiceservice.model.Invoice;
import com.garnerju.invoiceservice.service.InvoiceService;
import com.garnerju.invoiceservice.util.feign.CatalogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RefreshScope
@CrossOrigin(origins = {"http://localhost:3000"})
public class InvoiceController {

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private InvoiceService invoiceService;

    InvoiceController(CatalogClient catalogClient) {this.catalogClient = catalogClient;}

    @GetMapping("/invoice")
    public List<Invoice> getAllInvoices() {
        List<Invoice> invoiceList = invoiceService.getInvoices();

        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found.");
        } else {
            return invoiceList;
        }
        
    }

    @GetMapping("/invoice/{id}")
    public Invoice findInvoice(@PathVariable("id") long invoiceId) {
        Invoice invoice = invoiceService.getInvoicesById(invoiceId);
        if (invoice == null) {
            throw new IllegalArgumentException("Invoice could not be retrieved for id " + invoiceId);
        } else {
            return invoice;
        }
    }

    @GetMapping("/invoice/cname/{name}")
    @ResponseStatus(HttpStatus.OK)
    public List<Invoice> findInvoicesByCustomerName(@PathVariable("name") String name) {
        List<Invoice> invoiceList = invoiceService.getInvoicesByCustomerName(name);

        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found for: "+name);
        } else {
            return invoiceList;
        }
    }


    @PostMapping("/invoice")
    @ResponseStatus(HttpStatus.CREATED)
    public Invoice purchaseItem(@RequestBody @Valid Invoice invoice) {

       if(invoiceService.createInvoice(invoice) == null) { throw new RuntimeException("Invoice creation failed"); }
       else return invoice;
         }

    @DeleteMapping("/invoice/{id}")
    public void deleteInvoice(@RequestBody Invoice invoice) { invoiceService.deleteInvoice(invoice); }
    
    
}
