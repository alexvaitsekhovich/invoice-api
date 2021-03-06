package finance.invoicing.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

/**
 * Model for the invoices with positions list
 */

@JsonPropertyOrder({ "number", "date", "period", "debtorId", "totalNetto", "totalBrutto", "totalVatAmount", "positions" })
public class InvoiceDetailed {

    private List<InvoicePosition> positions;
    private Invoice invoice;

    private DateTimeFormatter formatter;

    public InvoiceDetailed() {
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    public void setPositions(List<InvoicePosition> positions) {
        this.positions = positions;
    }

    public void setInvoice(Invoice invoice) {
        this.invoice = invoice;
    }

    public String getNumber() {
        return invoice.getNumber();
    }

    public int getDebtorId() {
        return invoice.getDebtorId();
    }

    public LocalDateTime getDate() {
        return invoice.getDate();
    }

    public String getPeriod() {
        return invoice.getServiceFrom().format(formatter) + " - " +
                invoice.getServiceUntil().format(formatter);
    }

    public double getTotalNetto() {
        double totalNetto = 0.0;
        for(InvoicePosition pos: positions) {
            totalNetto += pos.getTotalNetto();
        }

        return (double) Math.round(totalNetto * 100) / 100;
    }

    public double getTotalBrutto() {
        double totalBrutto = 0.0;
        for(InvoicePosition pos: positions) {
            totalBrutto += pos.getTotalBrutto();
        }

        return (double) Math.round(totalBrutto * 100) / 100;
    }

    public double getTotalVatAmount() {
        return (double) Math.round( (getTotalBrutto() - getTotalNetto()) * 100) / 100;
    }

    public List<InvoicePosition> getPositions() {
        return positions;
    }
}