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

    private InvoicesService invoicesService;

    private DateTimeFormatter formatter;

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
        assertEquals(11, invoiceDetailed.getDebtorId());
        assertEquals(LocalDateTime.parse("2018-02-01 00:00:00", formatter), invoiceDetailed.getDate());
        assertEquals("R-1234", invoiceDetailed.getNumber());
        assertEquals(700, invoiceDetailed.getTotalNetto(), 0);
        assertEquals(761, invoiceDetailed.getTotalBrutto(), 0);
        assertEquals(61, invoiceDetailed.getTotalVatAmount(), 0);

        List<InvoicePosition> invoicePositions = invoiceDetailed.getPositions();
        for (InvoicePosition p: invoicePositions) {
            assertEquals(1, p.getInvoiceId());

            if (p.getId() == 1) {
                assertEquals("Position 1", p.getDescription());
                assertEquals(1, p.getAmount());
                assertEquals(100, p.getNetto(), 0);
                assertEquals(19, p.getVat(), 0);
            }
            else if (p.getId() == 2) {
                assertEquals("Position 2", p.getDescription());
                assertEquals(3, p.getAmount());
                assertEquals(200, p.getNetto(), 0);
                assertEquals(7, p.getVat(), 0);
            }
        }
    }

    /**
     * Test the listing of all invoices
     */
    @Test
    public void testListInvoices() {

        List<Invoice> invoices = invoicesService.getAllInvoicesEntries(11);
        assertEquals(6, invoices.size());

        for (Invoice i: invoices) {
            assertEquals(11, i.getDebtorId());
            assertEquals(0, i.getBalance(), 0);

            if (i.getId() == 1) {
                assertEquals("R-1234", i.getNumber());
                assertEquals(LocalDateTime.parse("2018-02-01 00:00:00", formatter), i.getDate());
                assertEquals(100, i.getNetto(), 0);
                assertEquals(119, i.getBrutto(), 0);
                assertEquals(LocalDateTime.parse("2018-01-01 00:00:00", formatter), i.getServiceFrom());
                assertEquals(LocalDateTime.parse("2018-01-31 00:00:00", formatter), i.getServiceUntil());
                assertEquals(1, i.getState());
            }
            else if (i.getId() == 2)  {
                assertEquals("R-5678", i.getNumber());
                assertEquals(LocalDateTime.parse("2019-03-01 00:00:00", formatter), i.getDate());
                assertEquals(200, i.getNetto(), 0);
                assertEquals(238, i.getBrutto(), 0);
                assertEquals(LocalDateTime.parse("2019-03-01 00:00:00", formatter), i.getServiceFrom());
                assertEquals(LocalDateTime.parse("2019-03-31 00:00:00", formatter), i.getServiceUntil());
                assertEquals(0, i.getState());
            }
        }
    }

    /**
     * Test the listing of invoices with pagination, page 0, should return full page
     */
    @Test
    public void testListInvoicesPage0() {

        List<Invoice> invoices = invoicesService.getInvoicesEntries(11, 0);
        assertEquals(InvoicesService.ENTRIES_PER_PAGE, invoices.size());
    }

    /**
     * Test the listing of invoices with pagination, page 1, should the remaining one
     */
    @Test
    public void testListInvoicesPage1() {

        List<Invoice> invoices = invoicesService.getInvoicesEntries(11, 1);
        assertEquals(1, invoices.size());
    }
}



