package finance.invoicing.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

/**
 * Model for the invoice data prediction position
 */

@JsonPropertyOrder({ "description", "netto"})
public class InvoicePredictionPosition {

    private String description;

    private double netto;

    public InvoicePredictionPosition(String description, double netto) {
        this.description = description;
        this.netto = (double) Math.round(netto * 100) / 100;
    }

    public String getDescription() {
        return description;
    }

    public double getNetto() {
        return netto;
    }
}