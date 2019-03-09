package finance.invoicing.unit;

import finance.invoicing.entity.Invoice;
import finance.invoicing.entity.InvoicePosition;
import finance.invoicing.model.InvoiceDetailed;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
public class InvoiceDetailedTest {

    @Test
    public void TestPosition1() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        List<InvoicePosition> positionsList = new ArrayList<>();

        Invoice invoice = new Invoice();
        invoice.setData(1, "R-1234", LocalDateTime.parse("2018-02-01 00:00:00", formatter), 100, 119, 0, 11,
                LocalDateTime.parse("2018-01-01 00:00:00", formatter), LocalDateTime.parse("2018-01-31 00:00:00", formatter), 1);

        InvoicePosition position1 = new InvoicePosition();
        position1.setData(1, 1, "Position 1", 1, 100, 19);
        positionsList.add(position1);

        InvoicePosition position2 = new InvoicePosition();
        position2.setData(2, 1, "Position 2", 6, 24.7, 12.1);
        positionsList.add(position2);

        InvoiceDetailed invoiceDetailed = new InvoiceDetailed();
        invoiceDetailed.setInvoice(invoice);
        invoiceDetailed.setPositions(positionsList);

        assertEquals(invoiceDetailed.getNumber(), "R-1234");
        assertEquals(invoiceDetailed.getPeriod(), "2018-01-01 00:00:00 - 2018-01-31 00:00:00");
        assertEquals(invoiceDetailed.getTotalNetto(), 248.2, 0.01);
        assertEquals(invoiceDetailed.getTotalBrutto(), 285.13, 0.01);
        assertEquals(invoiceDetailed.getTotalVatAmount(), 36.93, 0.01);
    }


}
