package finance.invoicing.model;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.util.List;

/**
 * Model for the invoice data prediction
 */

@JsonPropertyOrder({ "month", "positions" })
public class InvoicePrediction {

    private List<InvoicePredictionPosition> positions;

    private int month;

    public InvoicePrediction(int month, List<InvoicePredictionPosition> positions) {
        this.month = month;
        this.positions = positions;
    }

    // array of positions is used, so different algorithms can be used for different positions
    public List<InvoicePredictionPosition> getPositions() {
        return positions;
    }

    public int getMonth() {
        return month;
    }
}