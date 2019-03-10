package finance.invoicing.integration;

import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;
import finance.invoicing.model.InvoiceDetailed;
import finance.invoicing.repository.InvoicePositionsRepository;
import finance.invoicing.repository.InvoicesRepository;
import finance.invoicing.service.InvoicesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InvoiceServiceTest {
    @Autowired
    InvoicesRepository invoicesRepository;

    @Autowired
    InvoicePositionsRepository invoicePositionsRepository;

    InvoicesService invoicesService;

    DateTimeFormatter formatter;

    /**
     * Create invoices for testing
     */
    @Before
    public void setUp() {

        invoicesService = new InvoicesService(invoicesRepository, invoicePositionsRepository);

        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        LocalDateTime date = LocalDateTime.parse("2018-02-01 00:00:00", formatter);
        LocalDateTime from = LocalDateTime.parse("2018-01-01 00:00:00", formatter);
        LocalDateTime until = LocalDateTime.parse("2018-01-31 00:00:00", formatter);

        Invoice i1 = new Invoice();
        i1.setData(1, "R-1234", LocalDateTime.parse("2018-02-01 00:00:00", formatter), 100, 119, 0, 11,
                LocalDateTime.parse("2018-01-01 00:00:00", formatter), LocalDateTime.parse("2018-01-31 00:00:00", formatter), 1);
        invoicesRepository.save(i1);

        Invoice i2 = new Invoice();
        i2.setData(2, "R-5678", LocalDateTime.parse("2019-03-01 00:00:00", formatter), 200, 238, 0, 11,
                LocalDateTime.parse("2019-03-01 00:00:00", formatter), LocalDateTime.parse("2019-03-31 00:00:00", formatter), 0);
        invoicesRepository.save(i2);

        Invoice i3 = new Invoice();
        i3.setData(3, "Nan", date, 0, 0, 0, 11, from, until, 0);
        invoicesRepository.save(i3);

        Invoice i4 = new Invoice();
        i4.setData(4, "Nan", date, 0, 0, 0, 11, from, until, 0);
        invoicesRepository.save(i4);

        Invoice i5 = new Invoice();
        i5.setData(5, "Nan", date, 0, 0, 0, 11, from, until, 0);
        invoicesRepository.save(i5);

        Invoice i6 = new Invoice();
        i6.setData(6, "Nan", date, 0, 0, 0, 11, from, until, 0);
        invoicesRepository.save(i6);

        InvoicePosition ip1 = new InvoicePosition();
        ip1.setData(1, 1, "Position 1", 1, 100, 19);
        invoicePositionsRepository.save(ip1);

        InvoicePosition ip2 = new InvoicePosition();
        ip2.setData(2, 1, "Position 2", 3, 200, 7);
        invoicePositionsRepository.save(ip2);
    }

    /**
     * Test the detailed listing of the invoice
     */
    @Test
    public void testDetailedInvoice() {

        InvoiceDetailed invoiceDetailed = invoicesService.getInvoiceDetailed(1).get();
        assertEquals(invoiceDetailed.getDebtorId(), 11);
        assertEquals(invoiceDetailed.getDate(), LocalDateTime.parse("2018-02-01 00:00:00", formatter));
        assertEquals(invoiceDetailed.getNumber(), "R-1234");
        assertEquals(invoiceDetailed.getTotalNetto(), 700, 0);
        assertEquals(invoiceDetailed.getTotalBrutto(), 761, 0);
        assertEquals(invoiceDetailed.getTotalVatAmount(), 61, 0);

        List<InvoicePosition> invoicePositions = invoiceDetailed.getPositions();
        for (InvoicePosition p: invoicePositions) {
            assertEquals(p.getInvoiceId(), 1);

            if (p.getId() == 1) {
                assertEquals(p.getDescription(), "Position 1");
                assertEquals(p.getAmount(), 1);
                assertEquals(p.getNetto(), 100, 0);
                assertEquals(p.getVat(), 19, 0);
            }
            else if (p.getId() == 2) {
                assertEquals(p.getDescription(), "Position 2");
                assertEquals(p.getAmount(), 3);
                assertEquals(p.getNetto(), 200, 0);
                assertEquals(p.getVat(), 7, 0);
            }
        }
    }

    /**
     * Test the listing of all invoices
     */
    @Test
    public void testListInvoices() {

        List<Invoice> invoices = invoicesService.getAllInvoicesEntries(11);
        assertEquals(invoices.size(), 6);

        for (Invoice i: invoices) {
            assertEquals(i.getDebtorId(), 11);
            assertEquals(i.getBalance(), 0, 0);

            if (i.getId() == 1) {
                assertEquals(i.getNumber(), "R-1234");
                assertEquals(i.getDate(), LocalDateTime.parse("2018-02-01 00:00:00", formatter));
                assertEquals(i.getNetto(), 100, 0);
                assertEquals(i.getBrutto(), 119, 0);
                assertEquals(i.getServiceFrom(), LocalDateTime.parse("2018-01-01 00:00:00", formatter));
                assertEquals(i.getServiceUntil(), LocalDateTime.parse("2018-01-31 00:00:00", formatter));
                assertEquals(i.getState(), 1);
            }
            else if (i.getId() == 2)  {
                assertEquals(i.getNumber(), "R-5678");
                assertEquals(i.getDate(), LocalDateTime.parse("2019-03-01 00:00:00", formatter));
                assertEquals(i.getNetto(), 200, 0);
                assertEquals(i.getBrutto(), 238, 0);
                assertEquals(i.getServiceFrom(), LocalDateTime.parse("2019-03-01 00:00:00", formatter));
                assertEquals(i.getServiceUntil(), LocalDateTime.parse("2019-03-31 00:00:00", formatter));
                assertEquals(i.getState(), 0);
            }
        }
    }

    /**
     * Test the listing of invoices with pagination, page 0, should return full page
     */
    @Test
    public void testListInvoicesPage0() {

        List<Invoice> invoices = invoicesService.getInvoicesEntries(11, 0);
        assertEquals(invoices.size(), InvoicesService.ENTRIES_PER_PAGE);
    }

    /**
     * Test the listing of invoices with pagination, page 1, should the remaining one
     */
    @Test
    public void testListInvoicesPage1() {

        List<Invoice> invoices = invoicesService.getInvoicesEntries(11, 1);
        assertEquals(invoices.size(), 1);
    }
}



