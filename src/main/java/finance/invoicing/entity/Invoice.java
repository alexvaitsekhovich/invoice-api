package finance.invoicing.entity;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * Entity for the invoices
 */

@Entity
@Table(name = "invoices")
public class Invoice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String number;

    private LocalDateTime date;

    private double netto;

    private double brutto;

    private double balance;

    private int debtorId;

    private LocalDateTime serviceFrom;

    private LocalDateTime serviceUntil;

    private int state;

    public int getId() {
        return id;
    }

    public String getNumber() {
        return number;
    }

    public String getDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return date.format(formatter);
    }

    public int getDebtorId() {
        return debtorId;
    }

    public double getNetto() {
        return netto;
    }

    public double getBrutto() {
        return brutto;
    }

    public double getBalance() {
        return balance;
    }

    public String getServiceFrom() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return serviceFrom.format(formatter);
    }

    public String getServiceUntil() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return serviceUntil.format(formatter);
    }

    public int getState() {
        return state;
    }

    public void setData(int id, String number, String date, double netto, double brutto, double balance,
                   int debtorId, String serviceFrom, String serviceUntil, int state) {

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

        this.id = id;
        this.number = number;
        this.date =  LocalDateTime.parse(date, formatter);
        this.netto = netto;
        this.brutto = brutto;
        this.balance = balance;
        this.debtorId = debtorId;
        this.date =  LocalDateTime.parse(serviceFrom, formatter);
        this.date =  LocalDateTime.parse(serviceUntil, formatter);
        this.state = state;
    }
}