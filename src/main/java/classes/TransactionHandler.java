package classes;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class TransactionHandler {
    //static public List<Transaction> productLists = new ArrayList<>();
   static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
  static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
static Scanner scanner = new Scanner(System.in);
private static List<Transaction> transactions = new ArrayList<>();
    public static List<Transaction> getTransactions() {
        return transactions;
    }
public static List<Transaction> LoadTransactions(){

        try {
           // FileReader fileReader = new FileReader("transactions.csv");
           // BufferedReader bufReader = new BufferedReader(fileReader);
            BufferedReader bufReader = new BufferedReader (new FileReader("transactions.csv"));
            bufReader.readLine();
            String TransactionsRead;
            while ((TransactionsRead = bufReader.readLine()) != null) {
                String[] parts = TransactionsRead.split("\\|");
LocalDate date = LocalDate.parse(parts[0],dateFormatter);
LocalTime time = LocalTime.parse(parts[1], timeFormatter);
                    Transaction transaction = new Transaction( date, time, parts[2], parts[3], Double.parseDouble(parts[4]) ) ;
                    transactions.add(transaction);
            }
            bufReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            System.out.println("\nError reading file");
            e.printStackTrace();
        }
        return transactions;
    }
    public static void displayTransactions(List<Transaction> Transactions){
        for(Transaction transaction: Transactions){
            System.out.println(transaction);
        }
    }

    public static void TakeAPayment(){

        System.out.println("HOW MUCH?");
        double Amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("DESCRIPTION?");
        String Description = scanner.nextLine();
        System.out.println("VENDOR? By who?");
        String Vendor = scanner.nextLine();
        LocalTime Time= LocalTime.now();
        LocalDate Date = LocalDate.now();

        Transaction deposit = new Transaction(Date,Time,Description,Vendor,Amount);
        transactions.add(deposit);
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv" , true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("\n"+deposit.getDate().format(dateFormatter)+"|"+deposit.getTime().format(timeFormatter)+"|"+deposit.getDescription()+"|"+deposit.getVendor()+"|"+deposit.getAmount());
            bufWriter.close();
            System.out.println("Transaction recorded!");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    public static void MakeAPayment(){

        System.out.println("Enter 0(zero) to exit anytime");
        System.out.println("HOW MUCH?");
        double Amount = scanner.nextDouble();
        scanner.nextLine();
        System.out.println("DESCRIPTION?");
        String Description = scanner.nextLine();
        System.out.println("VENDOR? To who");
        String Vendor = scanner.nextLine();
        LocalTime Time= LocalTime.now();
        LocalDate Date = LocalDate.now();



        Transaction payment = new Transaction(Date,Time,Description,Vendor,-Math.abs(Amount));
        transactions.add(payment);
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv" , true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("\n"+payment.getDate().format(dateFormatter)+"|"+payment.getTime().format(timeFormatter)+"|"+payment.getDescription()+"|"+payment.getVendor()+"|"+payment.getAmount());
            bufWriter.close();
            System.out.println("Transaction recorded!");
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
