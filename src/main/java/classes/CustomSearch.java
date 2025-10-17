package classes;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class CustomSearch {
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";
    public static final String bold = "\u001B[1m";
    static Scanner scanner = new Scanner(System.in);
    //static List<Transaction> transactions = TransactionHandler.LoadTransactions();

    public static void customSearchMenu(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean running = true;
        while(running) {
            System.out.println(cyan +"*******************************Custom Search **********************************"+reset);
            List<Transaction> transactions = TransactionHandler.getTransactions();
            System.out.println("1. Search by Start Date.");
            System.out.println("2. Search by End Date");
            System.out.println("3. Search by Description");
            System.out.println("4. Search by Name");
            System.out.println("5. Search by Amount");
            System.out.println("0. Go back");
            String choice = scanner.nextLine();
            boolean validStartDate =false;
            String dateInput="";

            LocalDate filteredDate = null;

            int txnCntByStrtDate=0;
            int txnCntByEndDate=0;

            switch (choice) {

                case "1":

                    while (!validStartDate) { // loops until value is valid
                        System.out.println("Enter start date in this format (MM/dd/yyyy) ");
                        try {
                             dateInput= scanner.nextLine();
                            //make sure its parsed to the right format
                            filteredDate = LocalDate.parse(dateInput,dateFormatter);
                            validStartDate = true;  // when input is valid this makes the loop exit

                        } catch (DateTimeException e) {
                            System.out.println(bold+yellow+"Wrong Input! please enter a valid Date"+reset);
                            scanner.nextLine(); // clear invalid input entered
                        }
                    }

                    List<Transaction> startDateFilteredList = new ArrayList<>();
                    // loop through all transactions and add to new list if its after the input date
                    for( Transaction transaction: transactions){
                        if(transaction.getDate().isAfter(filteredDate)){
                            startDateFilteredList.add(transaction);
                            //count number of transactions
                            txnCntByStrtDate++;
                        }
                    }
                    if (startDateFilteredList.isEmpty()){
                        System.out.println("There are no transactions after that date");
                    }else {
                        System.out.println(" \nTransactions after "+ yellow+dateInput+"\n"+reset);
                        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                        System.out.println(cyan + "--------------------------------------------------------------------------"+reset);

                        for (Transaction transaction : startDateFilteredList) {
                            if(transaction.amount>0) {
                                System.out.println(green+transaction+reset);
                            }else
                                System.out.println(red+transaction+reset);
                        }
                        System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                        System.out.println(purple +txnCntByStrtDate + " transactions in total\n\n\n\n\n"+reset);
                    }
                    break;
                case "2":
                    // search by end date
                    while (!validStartDate) { // loops until value is valid
                        System.out.println("Enter start date in this format (MM/dd/yyyy) ");
                        try {
                            dateInput= scanner.nextLine();
                            //make sure its parsed to the right format
                            filteredDate = LocalDate.parse(dateInput,dateFormatter);
                            validStartDate = true;  // when input is valid this makes the loop exit

                        } catch (DateTimeException e) {
                            System.out.println(bold+yellow+"Wrong Input! please enter a valid Date"+reset);
                            scanner.nextLine(); // clear invalid input entered
                        }
                    }


                    // loop through all transactions and add to new list if its after the input date
                    List<Transaction> endDateFilteredList = new ArrayList<>();
                    for( Transaction transaction: transactions){
                        if(transaction.getDate().isBefore(filteredDate)){
                            endDateFilteredList.add(transaction);
                            txnCntByEndDate++;
                        }
                    }
                    if (endDateFilteredList.isEmpty()){
                        System.out.println("There are no transactions before that date");
                    }else {
                        System.out.println(" \nTransactions before "+ yellow+dateInput+"\n"+reset);
                        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                        System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                        for (Transaction transaction : endDateFilteredList) {
                            if(transaction.amount>0) {
                                System.out.println(green+transaction+reset);
                            }
                            else {
                                System.out.println(red+transaction+reset);
                            }
                        }
                        System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                        System.out.println(purple +txnCntByEndDate + " transactions in total\n\n\n\n\n"+reset);
                    }
                    break;
                case "3":
                    boolean validDescription =false;
                    String description="";
                    int descriptionMatch=0;
                    while (!validDescription)
                    {
                        System.out.println("Enter vendor name to search");

                        try {
                            description = scanner.nextLine().trim();
                            // make the user enter at least 3 characters for an effective search
                            if(description.length()<3){
                                System.out.println(bold+yellow+"please enter at least 3 characters to search "+reset);
                            }
                            else {
                                validDescription = true;  // when input is valid this makes the loop exit
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Wrong Input! please enter a valid description ");
                            scanner.nextLine(); // clear invalid input entered
                        }
                    }

                    List<Transaction> descriptionFilteredList = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        if (transaction.getDescription().toLowerCase().contains(description.toLowerCase())) { // doesnt have to be exact match
                            descriptionFilteredList.add(transaction);
                            descriptionMatch++;

                        }
                    }
                    if(descriptionFilteredList.isEmpty()){
                        System.out.println(bold+red+"NO Matching transaction found!"+reset);
                    }
                    else {
                        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT" + reset);
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        // print the list with the matches found
                        for (Transaction transaction : descriptionFilteredList) {
                            if (transaction.amount > 0) {
                                System.out.println(green + transaction + reset);
                            } else
                                System.out.println(red + transaction + reset);
                        }
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        System.out.println(purple + descriptionMatch + " transactions in total\n\n\n\n\n" + reset);
                    }

                    break;
                case "4":
                    boolean validVendor =false;
                    String vendorName ="";
                    while (!validVendor) { // loops until value is valid
                        System.out.println("Enter vendor name to search");

                        try {
                            vendorName = scanner.nextLine().trim();
                            // make the user enter at least 3 characters for an effective search
                            if(vendorName.length()<3){
                                System.out.println(bold+yellow+"please enter at least 3 characters to search "+reset);
                            }
                            else {
                                validVendor = true;  // when input is valid this makes the loop exit
                            }

                        } catch (InputMismatchException e) {
                            System.out.println("Wrong Input! please enter a valid vendor name ");
                            scanner.nextLine(); // clear invalid input entered
                        }
                    }

                    //loop through all out transactions and check the vendor name
                    int vendorMatch =0;
                    List<Transaction> vendorFilteredList = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        if (transaction.getVendor().toLowerCase().contains(vendorName.toLowerCase())) { // doesnt have to be exact match
                            vendorFilteredList.add(transaction);
                            vendorMatch++;

                        }
                    }
                    if(vendorFilteredList.isEmpty()){
                        System.out.println(bold+red+"NO Matching transaction found!"+reset);
                    }
                    else {
                        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT" + reset);
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        // print the list with the matches found
                        for (Transaction transaction : vendorFilteredList) {
                            if (transaction.amount > 0) {
                                System.out.println(green + transaction + reset);
                            } else
                                System.out.println(red + transaction + reset);
                        }
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        System.out.println(purple + vendorMatch + " transactions in total\n\n\n\n\n" + reset);
                    }
                    break;

                case "5":
                    int amountMatch=0;
                    boolean validAmount=false;
                    double amount =0;
                    while (!validAmount) { // loops until value is valid
                        System.out.println("Enter an amount ");

                        try {
                            amount = scanner.nextDouble();
                            scanner.nextLine();
                            validAmount = true;  // when input is valid this makes the loop exit

                        } catch (InputMismatchException e) {
                            System.out.println(bold+red+"Wrong Input! please enter an amount in numbers "+reset);
                            scanner.nextLine(); // clear invalid input entered
                        }
                    }


                    List<Transaction> amountFilteredList = new ArrayList<>();
                    for(Transaction transaction: transactions)
                    {
                        // compare both payment and deposit transactions by putting in abs
                        if(Math.abs(transaction.getAmount())>=amount){
                            amountFilteredList.add(transaction);
                            amountMatch++;
                        }
                    }
                    if(amountFilteredList.isEmpty()){
                        System.out.println("there is no transaction with that amount ");
                    }
                    else {
                        System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT" + reset);
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        for(Transaction transaction: amountFilteredList){
                            if(transaction.amount>0) {
                                System.out.println(green+transaction+reset);
                            }else
                                System.out.println(red+transaction+reset);
                        }
                        System.out.println(cyan + "--------------------------------------------------------------------------" + reset);
                        System.out.println(purple + amountMatch + " transactions in total\n\n\n\n\n" + reset);
                    }
                    break;
                case "0":
                    running=false;
                    break;
                default:
                    System.out.println("Invalid Choice try again:");
                    break;


            }
        }
    }
}
