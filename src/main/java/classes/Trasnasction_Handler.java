package classes;

import java.time.LocalDate;
import java.time.LocalTime;

public class Trasnasction_Handler {
    LocalDate Date;
    LocalTime Time;
    String Description;
    String Vendor;
    String amount;

    public Trasnasction_Handler(LocalDate date, LocalTime time, String description, String vendor, String amount) {
        Date = date;
        Time = time;
        Description = description;
        Vendor = vendor;
        this.amount = amount;
    }
    public Trasnasction_Handler(){

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

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    @Override
    public String toString() {
        return "Trasnasction_Handler{" +
                "Date=" + Date +
                ", Time=" + Time +
                ", Description='" + Description + '\'' +
                ", Vendor='" + Vendor + '\'' +
                ", amount='" + amount + '\'' +
                '}';
    }
}
