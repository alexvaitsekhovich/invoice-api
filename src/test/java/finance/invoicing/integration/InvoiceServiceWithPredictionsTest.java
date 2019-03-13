package finance.invoicing.integration;

import finance.invoicing.entity.Invoice;
import finance.invoicing.exception.InsufficientDataException;
import finance.invoicing.model.InvoicePredictionPosition;
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
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class InvoiceServiceWithPredictionsTest {
    @Autowired
    InvoicesRepository invoicesRepository;

    @Autowired
    InvoicePositionsRepository invoicePositionsRepository;

    InvoicesService invoicesService;

    DateTimeFormatter formatter;

    @Before
    public void setUp() {
        invoicesService = new InvoicesService(invoicesRepository, invoicePositionsRepository);
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    @Test
    public void TestClassicalPrediction1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        Invoice i1 = new Invoice();
        i1.setData(1, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-01-01 00:00:00", formatter), null, 1);
        invoiceList.add(i1);

        Invoice i2 = new Invoice();
        i2.setData(2, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-02-01 00:00:00", formatter), null, 1);
        invoiceList.add(i2);

        Invoice i3 = new Invoice();
        i3.setData(3, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-03-01 00:00:00", formatter), null, 1);
        invoiceList.add(i3);

        InvoicePredictionPosition classicalPrediction = invoicesService.calculateClassicalPredictionPosition(invoiceList);
        assertEquals(classicalPrediction.getNetto(), 100, 0);
    }

    @Test
    public void TestClassicalPrediction2() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        Invoice i1 = new Invoice();
        i1.setData(1, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-01-01 00:00:00", formatter), null, 1);
        invoiceList.add(i1);

        Invoice i2 = new Invoice();
        i2.setData(2, "Nan", null, 110, 0, 0, 0, LocalDateTime.parse("2018-02-01 00:00:00", formatter), null, 1);
        invoiceList.add(i2);

        Invoice i3 = new Invoice();
        i3.setData(3, "Nan", null, 120, 0, 0, 0, LocalDateTime.parse("2018-03-01 00:00:00", formatter), null, 1);
        invoiceList.add(i3);

        InvoicePredictionPosition classicalPrediction = invoicesService.calculateClassicalPredictionPosition(invoiceList);
        assertEquals(classicalPrediction.getNetto(), 105.6, 0);
    }

    @Test
    public void TestSeasonalPrediction1() throws InsufficientDataException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        for (int month = 1; month <= 12; month++) {
            String monthStr = String.format("%02d", month);

            Invoice i = new Invoice();
            i.setData(month, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-" + monthStr + "-01 00:00:00", formatter), null, 1);
            invoiceList.add(i);
        }

        InvoicePredictionPosition seasonalPrediction = invoicesService.calculateSeasonalPredictionPosition(invoiceList, 1);

        assertEquals(seasonalPrediction.getNetto(), 100.0, 0);
    }

    @Test
    public void TestSeasonalPrediction2() throws InsufficientDataException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        // add the deviating month
        Invoice i = new Invoice();
        i.setData(1, "Nan", null, 50.0, 0, 0, 0, LocalDateTime.parse("2018-01-01 00:00:00", formatter), null, 1);
        invoiceList.add(i);

        for (int month = 2; month <= 12; month++) {
            String monthStr = String.format("%02d", month);

            i = new Invoice();
            i.setData(month, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-" + monthStr + "-01 00:00:00", formatter), null, 1);
            invoiceList.add(i);
        }

        InvoicePredictionPosition seasonalPrediction = invoicesService.calculateSeasonalPredictionPosition(invoiceList, 1);

        assertEquals(seasonalPrediction.getNetto(), 49.93, 0);
    }

    // the needed month is not available in previous years
    @Test(expected = InsufficientDataException.class)
    public void TestSeasonalPrediction3() throws InsufficientDataException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        for (int month = 2; month <= 12; month++) {
            String monthStr = String.format("%02d", month);

            Invoice i = new Invoice();
            i.setData(month, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-" + monthStr + "-01 00:00:00", formatter), null, 1);
            invoiceList.add(i);
        }

        invoicesService.calculateSeasonalPredictionPosition(invoiceList, 1);
    }

    // only one month is available
    @Test(expected = InsufficientDataException.class)
    public void TestSeasonalPrediction4() throws InsufficientDataException {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<Invoice> invoiceList = new ArrayList<>();

        Invoice i1 = new Invoice();
        i1.setData(1, "Nan", null, 100, 0, 0, 0, LocalDateTime.parse("2018-02-01 00:00:00", formatter), null, 1);
        invoiceList.add(i1);

        invoicesService.calculateSeasonalPredictionPosition(invoiceList, 1);
    }

}



