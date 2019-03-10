package finance.invoicing.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Model for the invoice data prediction position
 */

@JsonPropertyOrder({ "description", "netto", "brutto", "balance"})
public class InvoicePredictionPosition {

    private String description;

    private double netto;

    private double brutto;

    private double balance;

    public InvoicePredictionPosition(String description, double netto, double brutto, double balance) {
        this.description = description;
        this.netto = (double) Math.round(netto * 100) / 100;
        this.brutto = (double) Math.round(brutto * 100) / 100;
        this.balance = (double) Math.round(balance * 100) / 100;
    }

    public String getDescription() {
        return description;
    }

    public double getNetto() {
        return netto;
    }

    public double getBrutto() {
        return brutto;
    }

    public double getBalance() {
        return balance;
    }

}