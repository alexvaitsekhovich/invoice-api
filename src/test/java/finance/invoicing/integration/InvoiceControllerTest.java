package finance.invoicing.integration;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import finance.invoicing.api.InvoiceController;
import finance.invoicing.entity.Invoice;
import finance.invoicing.service.InvoicesService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@RunWith(SpringRunner.class)
@ActiveProfiles("no-vault")
@WebMvcTest(controllers = InvoiceController.class, secure = false)
public class InvoiceControllerTest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private InvoicesService invoicesService;

    private ObjectMapper mapper;

    @Autowired
    MappingJackson2HttpMessageConverter builder;

    private DateTimeFormatter formatter;

    @Before
    public void setup() {
        this.mapper = builder.getObjectMapper();
        formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
    }

    /**
     * Test listing of all invoices
     *
     * @throws Exception
     */
    @Test
    public void testListAllInvoices() throws Exception {

        Invoice invoice = new Invoice();
        invoice.setData(1, "R-1234", LocalDateTime.parse("2018-02-01 00:00:00", formatter),
                100, 119, 0, 11,
                LocalDateTime.parse("2018-01-01 00:00:00", formatter),
                LocalDateTime.parse("2018-01-31 00:00:00", formatter), 1);

        List<Invoice> invoicesList = Collections.singletonList(invoice);
        given(invoicesService.getAllInvoicesEntries(11)).willReturn(invoicesList);

        // call the controller
        String response = mvc.perform(get("/invoicesall/11")).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Invoice> result = mapper.readValue(response, new TypeReference<List<Invoice>>() {});

        assertEquals(1, result.size());
        assertEquals(invoice.getId(), result.get(0).getId());
    }

    /**
     * Test listing of all invoices with pagination
     *
     * @throws Exception
     */
    @Test
    public void testListInvoicesPage0() throws Exception {

        List<Invoice> invoicesList = new ArrayList<>();

        LocalDateTime date = LocalDateTime.parse("2018-02-01 00:00:00", formatter);
        LocalDateTime from = LocalDateTime.parse("2018-01-01 00:00:00", formatter);
        LocalDateTime until = LocalDateTime.parse("2018-01-31 00:00:00", formatter);

        Invoice invoice1 = new Invoice();
        invoice1.setData(1, "R-1234", date, 100, 119, 0, 11, from, until, 1);
        invoicesList.add(invoice1);

        Invoice invoice2 = new Invoice();
        invoice2.setData(2, "Nan", date, 0, 0, 0, 11, from, until, 1);
        invoicesList.add(invoice2);

        Invoice invoice3 = new Invoice();
        invoice3.setData(3, "Nan", date, 0, 0, 0, 11, from, until, 1);
        invoicesList.add(invoice3);

        Invoice invoice4 = new Invoice();
        invoice4.setData(4, "Nan", date, 0, 0, 0, 11, from, until, 1);
        invoicesList.add(invoice4);

        Invoice invoice5 = new Invoice();
        invoice5.setData(5, "Nan", date, 0, 0, 0, 11, from, until, 1);
        invoicesList.add(invoice5);

        given(invoicesService.getInvoicesEntries(11, 0)).willReturn(invoicesList);

        // call the controller
        String response = mvc.perform(get("/invoices/11")).
                andExpect(status().isOk()).andReturn().getResponse().getContentAsString();
        List<Invoice> result = mapper.readValue(response, new TypeReference<List<Invoice>>() {});

        assertEquals(InvoicesService.ENTRIES_PER_PAGE, result.size());
    }
}




