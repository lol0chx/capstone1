package classes;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Transaction {
    LocalDate Date;
    LocalTime Time;
    String Description;
    String Vendor;
    double amount;
    static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    public Transaction(LocalDate date, LocalTime time, String description, String vendor, double amount) {
        Date = date;
        Time = time;
        Description = description;
        Vendor = vendor;
        this.amount = amount;
    }
    public Transaction(){

    }

    public LocalDate getDate() {
        return Date;
    }

    public void setDate(LocalDate date) {
        Date = date;
    }

    public LocalTime getTime() {
        return Time;
    }

    public void setTime(LocalTime time) {
        Time = time;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getVendor() {
        return Vendor;
    }

    public void setVendor(String vendor) {
        Vendor = vendor;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Transaction{" +
                "Date=" + Date.format(dateFormatter) +
                ", Time=" + Time.format(timeFormatter) +
                ", Description='" + Description + '\'' +
                ", Vendor='" + Vendor + '\'' +
                ", amount=" + amount +
                '}';
    }
}
