package finance.invoicing.service;

import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;
import finance.invoicing.exception.InsufficientDataException;
import finance.invoicing.exception.NoInvoicesException;
import finance.invoicing.helper.Statistics;
import finance.invoicing.model.InvoiceDetailed;
import finance.invoicing.model.InvoicePrediction;
import finance.invoicing.model.InvoicePredictionPosition;
import finance.invoicing.repository.InvoicePositionsRepository;
import finance.invoicing.repository.InvoicesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.averagingDouble;

/**
 * Service for the invoicing
 */

@Service
public class InvoicesService {

    public static final int ENTRIES_PER_PAGE = 5;

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
        logger.info("Retrieve the invoices of the debtor #{}, page {}", debtorId, page);

        Pageable pageable = PageRequest.of(page, ENTRIES_PER_PAGE);

        return invoicesRepository.getByDebtorIdOrderByDateDesc(debtorId, pageable);
    }

    /**
     * Get all invoices from the database
     *
     * @param debtorId - the debtor id
     * @return list of invoices
     */
    public List<Invoice> getAllInvoicesEntries(int debtorId) {
        logger.info("Retrieve all invoices of the debtor #{}", debtorId);

        return invoicesRepository.getByDebtorIdOrderByDateDesc(debtorId);
    }

    /**
     * Get all positions of this invoice with the invoice details
     *
     * @param invoiceId - invoice id
     * @return detailed invoice
     */
    public Optional<InvoiceDetailed> getInvoiceDetailed(int invoiceId) {
        logger.info("Retrieve all positions of the invoice #{} with details of the invoice", invoiceId);

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

    /**
     * Get the invoice prediction for the next month
     *
     * @param debtorId - the debtor id
     * @return list of invoices
     */
    public InvoicePrediction getMonthPrediction(int debtorId) throws NoInvoicesException, InsufficientDataException {
        logger.info("Get the invoice predictions of the debtor #{} for the next month", debtorId);

        List<Invoice> invoicesList = invoicesRepository.getByDebtorIdOrderByDateAsc(debtorId);

        if (invoicesList.isEmpty()) {
            throw new NoInvoicesException("No invoices found for debtor #" + debtorId);
        }

        if (invoicesList.size() < 2) {
            throw new InsufficientDataException("Not enough invoices found for debtor #" + debtorId);
        }

        // month should be 1 if the last invoice was made in December
        int nextMonth = Math.max(1, (invoicesList.get(invoicesList.size() - 1).getServiceFrom().getMonthValue() + 1) % 13);

        List<InvoicePredictionPosition> invoicePredictionPositions = new ArrayList<>();

        InvoicePredictionPosition classicalPrediction = calculateClassicalPredictionPosition(invoicesList);
        invoicePredictionPositions.add(classicalPrediction);

        InvoicePredictionPosition seasonalPrediction;

        // add seasonal prediction only if the month is available in the previous years
        try {
            seasonalPrediction = calculateSeasonalPredictionPosition(invoicesList, nextMonth);
            invoicePredictionPositions.add(seasonalPrediction);
        }
        catch (InsufficientDataException e) {
            logger.info("No invoices found for month {}, cannot calculate seasonal prediction", nextMonth);
        }

        return new InvoicePrediction(nextMonth, invoicePredictionPositions);
    }

    /**
     * Calculate invoice prediction for the next month, i.e. the next after the last invoice,
     * based on classical algorithm ignoring seasonal deviation
     * declared public for usage in testcase
     *
     * @param invoicesList - invoice id
     * @return invoice prediction position
     */
    public InvoicePredictionPosition calculateClassicalPredictionPosition(List<Invoice> invoicesList) {
        // calculate the predicted values with factor 0.2 for the current month
        // and factor 0.8 for the previous accumulated months
        double predictedNetto = Statistics.calculatePrediction(
                invoicesList.stream().map(Invoice::getNetto).collect(Collectors.toList()),
                0.2);

        return new InvoicePredictionPosition("Classical", predictedNetto);
    }

    /**
     * Calculate invoice prediction for the next month, i.e. the next after the last invoice,
     * based on classical algorithm considering seasonal deviation
     * declared public for usage in testcase
     *
     * @param invoicesList - invoice id
     * @return invoice prediction position
     */
    public InvoicePredictionPosition calculateSeasonalPredictionPosition(List<Invoice> invoicesList, int month) throws InsufficientDataException {

        // retrieve invoice netto for the specified month of every year
        Map<Integer, Double> monthNettoForMonth = invoicesList.stream().
                filter(el -> el.getServiceFrom().getMonthValue() == month).
                collect(Collectors.toMap(Invoice::getYear, Invoice::getNetto));

        if (monthNettoForMonth.isEmpty()) {
            throw new InsufficientDataException("No invoices found for the month " + month);
        }

        // calculate the predicted values with factor 0.2 for the current month
        // and factor 0.8 for the previous accumulated months
        double predictedNetto = Statistics.calculatePrediction(
                invoicesList.stream().map(Invoice::getNetto).collect(Collectors.toList()),
                0.2);

        // calculate avg netto per year, ignore year that have no invoice in the specified month
        Map<Integer, Double> averageNettoPerYear = invoicesList.stream().
                filter(el -> monthNettoForMonth.get(el.getServiceFrom().getYear()) != null).
                collect(Collectors.groupingBy(Invoice::getYear, averagingDouble(Invoice::getNetto)));

        double seasonalPrediction = Statistics.calculateSeasonalPrediction(predictedNetto,
                monthNettoForMonth, averageNettoPerYear);

        return new InvoicePredictionPosition("Seasonal", seasonalPrediction);
    }
}
