package finance.invoicing.api;

import finance.invoicing.entity.Invoice;
import finance.invoicing.exception.InsufficientDataException;
import finance.invoicing.exception.NoInvoicesException;
import finance.invoicing.model.InvoiceDetailed;
import finance.invoicing.model.InvoicePrediction;
import finance.invoicing.service.InvoicesService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class InvoiceController {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private InvoicesService invoicesService;

    /**
     * Get debtor's invoices with pagination
     * @param debtorId debtor id
     * @param page - page offset
     *
     * @return the list of invoices
     */
    @GetMapping(value = "/invoices/{debtorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Invoice>> getInvoices(@PathVariable Integer debtorId,
                                                     @RequestParam(name = "page", required = false, defaultValue = "0") int page) {
        logger.info("Retrieve the invoices of the debtor #{}, page {}", debtorId, page);

        List<Invoice> invoicesList = invoicesService.getInvoicesEntries(debtorId, page);

        logger.info("Returning the invoices list");
        return ResponseEntity.ok(invoicesList);
    }

    /**
     * Get all debtor's invoices without pagination
     * @param debtorId - debtor id
     *
     * @return the list of invoices
     */
    @GetMapping(value = "/invoicesall/{debtorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Invoice>> getAllInvoices(@PathVariable Integer debtorId) {
        logger.info("Retrieve all invoices of the debtor #{}", debtorId);

        List<Invoice> invoicesList = invoicesService.getAllInvoicesEntries(debtorId);

        logger.info("Returning the invoices list");
        return ResponseEntity.ok(invoicesList);
    }

    /**
     * Get all invoice positions and all details of the invoice
     * @param invoiceId - debtor id
     * @return the detailed invoice
     */
    @GetMapping(value = "/invoicedetailed/{invoiceId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoiceDetailed> getInvoiceDetailed(@PathVariable Integer invoiceId) {
        logger.info("Retrieve all positions and details of the invoice #{}", invoiceId);

        Optional<InvoiceDetailed> invoiceDetailed = invoicesService.getInvoiceDetailed(invoiceId);

        if (!invoiceDetailed.isPresent()) {
            logger.info("Invoice with id {} not found", invoiceId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Returning the invoice details and positions list");
        return ResponseEntity.ok(invoiceDetailed.get());
    }

    /**
     * Get the invoice amount prediction for the certain month
     * @param debtorId - debtor id
     *
     * @return the prediction about the invoice data
     */
    @GetMapping(value = "/invoiceprediction/{debtorId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<InvoicePrediction> getInvoicePrediction(@PathVariable Integer debtorId) {
        logger.info("Get the invoice prediction of the debtor #{} for the next month", debtorId);

        InvoicePrediction invoicePrediction;

        try {
            invoicePrediction = invoicesService.getMonthPrediction(debtorId);
        }
        catch (NoInvoicesException e) {
            logger.info("No invoices found for debtor #{}", debtorId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        catch (InsufficientDataException e) {
            logger.info("Not enough data is available for the debtor #{}", debtorId);
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        logger.info("Returning the invoices prediction");
        return ResponseEntity.ok(invoicePrediction);
    }

}
