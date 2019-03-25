package finance.invoicing.unit;

import finance.invoicing.entity.InvoicePosition;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.assertEquals;


@RunWith(SpringRunner.class)
public class InvoicePositionTest {

    @Test
    public void TestPosition1() {

        InvoicePosition position = new InvoicePosition();
        position.setData(1, 1, "Position 1", 1, 100, 19);

        assertEquals(100, position.getTotalNetto(), 0.01);
        assertEquals(119, position.getTotalBrutto(), 0.01);
        assertEquals(19, position.getTotalVat(), 0.01);
    }

    @Test
    public void TestPosition2() {

        InvoicePosition position = new InvoicePosition();
        position.setData(1, 1, "Position 2", 6, 24.7, 12.1);

        assertEquals(148.2, position.getTotalNetto(), 0.01);
        assertEquals(166.13, position.getTotalBrutto(), 0.01);
        assertEquals(17.93, position.getTotalVat(), 0.01);
    }

}
