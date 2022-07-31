package com.garnerju.invoiceservice.service;

import com.garnerju.invoiceservice.model.*;
import com.garnerju.invoiceservice.repository.InvoiceRepository;
import com.garnerju.invoiceservice.repository.ProcessingFeeRepository;
import com.garnerju.invoiceservice.repository.TaxRepository;
import com.garnerju.invoiceservice.util.feign.CatalogClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RefreshScope
public class InvoiceService {

    @Autowired
    private CatalogClient catalogClient;
    @Autowired
    private InvoiceRepository invoiceRepository;
    @Autowired
    private TaxRepository taxRepository;
    @Autowired
    private ProcessingFeeRepository processingFeeRepository;

    private final BigDecimal PROCESSING_FEE = new BigDecimal("15.49");
    private final BigDecimal MAX_INVOICE_TOTAL = new BigDecimal("999.99");
    private final String GAME_ITEM_TYPE = "Game";
    private final String CONSOLE_ITEM_TYPE = "Console";
    private final String TSHIRT_ITEM_TYPE = "T-Shirt";

    public List<Invoice> getInvoices() {

        List<Invoice> invoiceList = invoiceRepository.findAll();

        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found.");
        } else {
            return invoiceList;
        }
    }

    public Invoice getInvoicesById(Long id) { return invoiceRepository.findById(id).orElse(null); }

    public void deleteInvoice(Invoice invoice) { invoiceRepository.deleteById(invoice.getId());   }

    public Invoice createInvoice(Invoice invoice) {


        //validation...
        if (invoice==null)
            throw new NullPointerException("Create invoice failed. no invoice data.");

        if(invoice.getItemType()==null)
            throw new IllegalArgumentException("Unrecognized Item type. Valid ones: Console or Game or T-Shirt");

        //Check Quantity is > 0...
        if(invoice.getQuantity()<=0){
            throw new IllegalArgumentException(invoice.getQuantity() +
                    ": Unrecognized Quantity. Must be > 0.");
        }


        //Checks the item type and get the correct unit price
        //Check if we have enough quantity
        if (invoice.getItemType().equals(CONSOLE_ITEM_TYPE)) {

                Console tempCon = null;
                Optional<Console> returnVal = Optional.ofNullable(catalogClient.getConsoleById(invoice.getItemId()));

                if (returnVal.isPresent()) {
                    tempCon = returnVal.get();
                } else {
                    throw new IllegalArgumentException("Requested console is not there");
                }

                if (invoice.getQuantity() > tempCon.getQuantity()) {
                    throw new IllegalArgumentException("Requested console quantity is more than in stock.");
                }

                invoice.setUnitPrice(tempCon.getPrice());


            } else if (invoice.getItemType().equals(GAME_ITEM_TYPE)) {

                Game tempGame = null;
                Optional<Game> returnVal = Optional.ofNullable(catalogClient.getGameById(invoice.getItemId()));

                if (returnVal.isPresent()) {
                    tempGame = returnVal.get();
                } else {
                    throw new IllegalArgumentException("Requested game is unavailable.");
                }

                if (invoice.getQuantity() > tempGame.getQuantity()) {
                    throw new IllegalArgumentException("Requested game quantity is unavailable.");
                }
                invoice.setUnitPrice(tempGame.getPrice());

            } else if (invoice.getItemType().equals(TSHIRT_ITEM_TYPE)) {
                TShirt tempTShirt = null;
                Optional<TShirt> returnVal = Optional.ofNullable(catalogClient.getTShirtById(invoice.getItemId()));

                if (returnVal.isPresent()) {
                    tempTShirt = returnVal.get();
                } else {
                    throw new IllegalArgumentException("Requested shirt is unavailable.");
                }

                if (invoice.getQuantity() > tempTShirt.getQuantity()) {
                    throw new IllegalArgumentException("Requested shirt quantity is unavailable.");
                }
                invoice.setUnitPrice(tempTShirt.getPrice());

                }

        else  throw new IllegalArgumentException(invoice.getItemType() +
                        ": Unrecognized Item type. Valid ones: T-Shirt, Console, or Game");


        invoice.setQuantity(invoice.getQuantity());

        invoice.setSubtotal(
                invoice.getUnitPrice().multiply(
                        new BigDecimal(invoice.getQuantity())).setScale(2, RoundingMode.HALF_UP));

        //Throw Exception if subtotal is greater than 999.99
        if ((invoice.getSubtotal().compareTo(new BigDecimal(999.99)) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        //Validate State and Calc tax...
        BigDecimal tempTaxRate;
        Optional<Tax> returnVal = taxRepository.findById(invoice.getState());

        if (returnVal.isPresent()) {
            tempTaxRate = returnVal.get().getRate();
        } else {
            throw new IllegalArgumentException(invoice.getState() + ": Invalid State code.");
        }

        if (!tempTaxRate.equals(BigDecimal.ZERO))
            invoice.setTax(tempTaxRate.multiply(invoice.getSubtotal()));
        else
            throw new IllegalArgumentException( invoice.getState() + ": Invalid State code.");

        BigDecimal processingFee;
        Optional<ProcessingFee> returnVal2 = processingFeeRepository.findById(invoice.getItemType());

        if (returnVal2.isPresent()) {
            processingFee = returnVal2.get().getFee();
        } else {
            throw new IllegalArgumentException("Processing Fee not found for this item type.");
        }

        invoice.setProcessingFee(processingFee);

        //Checks if quantity of items if greater than 10 and adds additional processing fee
        if (invoice.getQuantity() > 10) {
            invoice.setProcessingFee(invoice.getProcessingFee().add(PROCESSING_FEE));
        }

        invoice.setTotal(invoice.getSubtotal().add(invoice.getProcessingFee()).add(invoice.getTax()));

        //checks total for validation
        if ((invoice.getTotal().compareTo(MAX_INVOICE_TOTAL) > 0)) {
            throw new IllegalArgumentException("Subtotal exceeds maximum purchase price of $999.99");
        }

        return  invoiceRepository.save(invoice);

    }

    public List<Invoice> getInvoicesByCustomerName(String customerName) {

        List<Invoice> invoiceList = invoiceRepository.findByName(customerName);

        if (invoiceList == null || invoiceList.isEmpty()) {
            throw new IllegalArgumentException("No invoices were found.");
        } else {
            return invoiceList;
        } }

}

