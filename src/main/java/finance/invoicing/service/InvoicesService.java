package finance.invoicing.service;

import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;
import finance.invoicing.model.InvoiceDetailed;
import finance.invoicing.repository.InvoicePositionsRepository;
import finance.invoicing.repository.InvoicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Service for the invoicing
 */

@Service
public class InvoicesService {

    private static final int ENTRIES_PER_PAGE = 5;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private InvoicesRepository invoicesRepository;
    private InvoicePositionsRepository invoicePositionsRepository;

    @Autowired
    public InvoicesService(InvoicesRepository invoicesRepository, InvoicePositionsRepository invoicePositionsRepository) {
        this.invoicesRepository = invoicesRepository;
        this.invoicePositionsRepository = invoicePositionsRepository;
    }

    /**
     * Get invoices from the database with pagination
     *
     * @param debtorId - the debtor id
     * @param page     - offset from the start
     * @return list of invoices
     */
    public List<Invoice> getInvoicesEntries(int debtorId, int page) {
        logger.debug("Retrieve the invoices of the debtor #{}, page {}", debtorId, page);

        Pageable pageable = PageRequest.of(page, ENTRIES_PER_PAGE);

        List<Invoice> invoicesList = invoicesRepository.getByDebtorIdOrderByDateDesc(debtorId, pageable);

        return invoicesList;
    }

    /**
     * Get all invoices from the database
     *
     * @param debtorId - the debtor id
     * @return list of invoices
     */
    public List<Invoice> getAllInvoicesEntries(int debtorId) {
        logger.debug("Retrieve all invoices of the debtor #{}", debtorId);

        List<Invoice> invoicesList = invoicesRepository.getByDebtorIdOrderByDateDesc(debtorId);

        return invoicesList;
    }

    /**
     * Get all positions of this invoice with the invoice details
     *
     * @param invoiceId
     * @return detailed invoice
     */
    public Optional<InvoiceDetailed> getInvoiceDetailed(int invoiceId) {
        logger.debug("Retrieve all positions of the invoice #{} with details of the invoice", invoiceId);

        Invoice invoice = invoicesRepository.getById(invoiceId);

        if (invoice == null) {
            return Optional.empty();
        }

        List<InvoicePosition> invoicePositionList = invoicePositionsRepository.getByInvoiceId(invoiceId);

        InvoiceDetailed invoiceDetailed = new InvoiceDetailed();

        invoiceDetailed.setInvoice(invoice);
        invoiceDetailed.setPositions(invoicePositionList);

        return Optional.of(invoiceDetailed);
    }
}
