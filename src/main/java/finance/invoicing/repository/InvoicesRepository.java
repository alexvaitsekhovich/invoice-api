package finance.invoicing.repository;

import finance.invoicing.entity.Invoice;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository for the invoices
 */

@Repository
public interface InvoicesRepository extends JpaRepository<Invoice, Integer> {

    /**
     * Get the list of invoices of the defined debtor with pagination
     *
     * @return all invoices of this debtor
     */
    List<Invoice> getByDebtorIdOrderByDateDesc(int debtorId, Pageable page);

    /**
     * Get the list of invoices of the defined debtor without pagination
     *
     * @return all invoices of this debtor
     */
    List<Invoice> getByDebtorIdOrderByDateDesc(int debtorId);

    /**
     * Get the list of invoices of the defined debtor in ascending order
     *
     * @return all invoices of this debtor
     */
    List<Invoice> getByDebtorIdOrderByDateAsc(int debtorId);

    /**
     * Get the invoice by id
     *
     * @return invoice
     */
    Invoice getById(int invoiceId);
}
