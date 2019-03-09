package finance.invoicing.repository;

import finance.invoicing.entity.InvoicePosition;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the invoice positions
 */

@Repository
public interface InvoicePositionsRepository extends JpaRepository<InvoicePosition, Integer> {

    /**
     * Get the list of positions from the defined invoice
     * @return all invoices in this invoice
     */
    List<InvoicePosition> getByInvoiceId(int invoiceId);
}
