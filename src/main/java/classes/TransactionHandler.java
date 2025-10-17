package classes;

import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class TransactionHandler {
    // define ANSI color codes
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";
    public static final String bold = "\u001B[1m";
    // Define our date and time formatter here
   static DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
   static DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");

   static Scanner scanner = new Scanner(System.in);
    private static final List<Transaction> transactions = new ArrayList<>();
    //return our transaction list to access is in any class without having to load everytime
    public static List<Transaction> getTransactions() {
        return transactions;
    }
    // define load transactions method
public static List<Transaction> LoadTransactions(){

        try {

            BufferedReader bufReader = new BufferedReader (new FileReader("transactions.csv")); // reads the file
            bufReader.readLine(); // skips the first line
            String TransactionsRead;
            while ((TransactionsRead = bufReader.readLine()) != null) {
                String[] parts = TransactionsRead.split("\\|");
                LocalDate date = LocalDate.parse(parts[0],dateFormatter); // parse the date to MM/dd/yyyy format
                LocalTime time = LocalTime.parse(parts[1], timeFormatter);
                    Transaction transaction = new Transaction( date, time, parts[2], parts[3], Double.parseDouble(parts[4]) ) ;
                    transactions.add(transaction);
            }
            bufReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            System.out.println(red+bold+"\nError reading file"+reset);
            e.printStackTrace();
        }
        return transactions;
    }
    public static void displayTransactions(List<Transaction> Transactions){
        int numOfTransactions=0;
        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
        for(Transaction transaction: Transactions){
            //if its a deposit make it green
            if(transaction.amount>0){
            System.out.println(green +transaction+reset);
            }
            else
                System.out.println(red +transaction+reset);
            numOfTransactions++; // count number of transactions
        }
       System.out.println(cyan + "-------------------------------------------------------------------------"+reset);
        System.out.println(purple +numOfTransactions + " transactions in total\n\n\n\n\n"+reset);
    }


    // define  a take a payment method
    public static void takePayment(){
        double amount =0;
        String description ="";
        String vendor="";
        boolean validAmount = false;
        boolean validDescription = false;
        boolean validVendor = false;

        // Ask for an amount input and handle wrong input for amount
        while (!validAmount) { // loops until value is valid
            System.out.println("How much? Enter an amount ");

            try {
                amount = scanner.nextDouble();
                scanner.nextLine();
                validAmount = true;  // when input is valid this makes the loop exit

            } catch (InputMismatchException e) {
                System.out.println(bold+red+"Wrong Input! please enter an amount in $ "+reset);
                scanner.nextLine(); // clear invalid input entered
            }
        }
        // Ask for description input and handle wrong input for description

            System.out.println("Enter description(what are you selling) ");
            description = scanner.nextLine();
        // Ask for vendor input and handle wrong input for description
            System.out.println("To who(Vendor)??");
            vendor = scanner.nextLine();



        LocalTime time= LocalTime.now();
        LocalDate date = LocalDate.now();
        // create a Transaction object name deposit
        Transaction deposit = new Transaction(date,time,description,vendor,amount);
        transactions.add(deposit);// add it to our transactions list
        // write the record to transaction file
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv" , true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("\n"+deposit.getDate().format(dateFormatter)+"|"+deposit.getTime().format(timeFormatter)+"|"+deposit.getDescription()+"|"+deposit.getVendor()+"|"+deposit.getAmount());
            bufWriter.close();
            System.out.println(green +"Transaction successfully recorded!"+ reset);
            System.out.println("Amount: "+ amount + "\nDescription: "+ description + "\nvendor: "+ vendor);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    // define makepayment method to handle payments
    public static void makePayment(){

        double amount =0;
        String description ="";
        String vendor="";
        boolean validAmount = false;
        boolean validDescription = false;
        boolean validVendor = false;

        // Ask for an amount input and handle wrong input for amount
        while (!validAmount) { // loops until value is valid
            System.out.println("How much? Enter an amount ");

            try {
                amount = scanner.nextDouble();
                scanner.nextLine();
                validAmount = true;  // when input is valid this makes the loop exit

            } catch (InputMismatchException e) {
                System.out.println(bold+red+"Wrong Input! please enter an amount in $ "+reset);
                scanner.nextLine(); // clear invalid input entered
            }
        }
        // Ask for description input and handle wrong input for description
            System.out.println("Description? what are you buying ");
                description = scanner.nextLine();
        // Ask for vendor input and handle wrong input for description
            System.out.println("Vendor? From who??");
                vendor = scanner.nextLine();
        LocalTime Time= LocalTime.now();
        LocalDate Date = LocalDate.now();

        // create a Transaction Object named payment
        Transaction payment = new Transaction(Date,Time,description,vendor,-Math.abs(amount)); // use Transaction constructor to assign the values
        transactions.add(payment); // add is to our transactions list
        //write it to our transaction file
        try {
            FileWriter fileWriter = new FileWriter("transactions.csv" , true);
            BufferedWriter bufWriter = new BufferedWriter(fileWriter);
            bufWriter.write("\n"+payment.getDate().format(dateFormatter)+"|"+payment.getTime().format(timeFormatter)+"|"+payment.getDescription()+"|"+payment.getVendor()+"|"+payment.getAmount());
            bufWriter.close();
            System.out.println(green +"Transaction successfully recorded!"+ reset);
            System.out.println("Amount: "+ amount + "\nDescription: "+ description + "\nvendor: "+ vendor);
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }



}
