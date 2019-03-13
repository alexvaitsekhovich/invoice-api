package finance.invoicing.entity;

import javax.persistence.*;
import java.time.LocalDateTime;

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

    public LocalDateTime getDate() {
        return date;
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

    public LocalDateTime getServiceFrom() {
        return serviceFrom;
    }

    public LocalDateTime getServiceUntil() {
        return serviceUntil;
    }

    public int getState() {
        return state;
    }

    public int getYear(){
        return serviceFrom.getYear();
    }

    public int getMonth(){
        return serviceFrom.getMonthValue();
    }

    public void setData(int id, String number, LocalDateTime date, double netto, double brutto, double balance,
                   int debtorId, LocalDateTime serviceFrom, LocalDateTime serviceUntil, int state) {

        this.id = id;
        this.number = number;
        this.date =  date;
        this.netto = netto;
        this.brutto = brutto;
        this.balance = balance;
        this.debtorId = debtorId;
        this.serviceFrom =  serviceFrom;
        this.serviceUntil =  serviceUntil;
        this.state = state;
    }
}