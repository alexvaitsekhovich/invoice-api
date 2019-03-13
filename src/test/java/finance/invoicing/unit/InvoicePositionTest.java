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

        assertEquals(position.getTotalNetto(), 100, 0.01);
        assertEquals(position.getTotalBrutto(), 119, 0.01);
        assertEquals(position.getTotalVat(), 19, 0.01);
    }

    @Test
    public void TestPosition2() {

        InvoicePosition position = new InvoicePosition();
        position.setData(1, 1, "Position 2", 6, 24.7, 12.1);

        assertEquals(position.getTotalNetto(), 148.2, 0.01);
        assertEquals(position.getTotalBrutto(), 166.13, 0.01);
        assertEquals(position.getTotalVat(), 17.93, 0.01);
    }

}
