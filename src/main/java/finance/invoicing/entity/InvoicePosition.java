package finance.invoicing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;

/**
 * Entity for the invoice positions
 */

@Entity
@Table(name = "invoice_positions")
@JsonIgnoreProperties({"id"})
public class InvoicePosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private int invoiceId;

    private String description;

    private int amount;

    private double netto;

    private double vat;

    public int getInvoiceId() {
        return invoiceId;
    }

    public String getDescription() {
        return description;
    }

    public int getAmount() {
        return amount;
    }

    public double getNetto() {
        return netto;
    }

    public double getVat() {
        return vat;
    }

    @JsonIgnore
    public double getTotalNetto() {
        return netto * amount;
    }

    @JsonIgnore
    public double getTotalBrutto() {
        return (netto * amount) * (1 + vat/100);
    }

    @JsonIgnore
    public double getTotalVat() {
        return (netto * amount) * (vat/100);
    }

}