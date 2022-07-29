package com.garnerju.invoiceservice.service;

import com.garnerju.invoiceservice.model.Invoice;
import com.garnerju.invoiceservice.repository.InvoiceRepository;
import com.garnerju.invoiceservice.util.feign.CatalogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RefreshScope
public class InvoiceService {

    @Autowired
    private CatalogClient catalogClient;

    @Autowired
    private InvoiceRepository invoiceRepository;


    public List<Invoice> getInvoices() {
        return invoiceRepository.findAll();
    }

    public Invoice getInvoicesById(Long id) {
        return invoiceRepository.findById(id).orElse(null);
    }

    public Invoice createInvoice(Invoice invoice) {
        return invoiceRepository.save(invoice);
    }

    public void updateInvoice(Invoice invoice) {
        invoiceRepository.save(invoice);
    }

    public void deleteInvoice(Invoice invoice) {
        invoiceRepository.deleteById(invoice.getId());
    }
}
