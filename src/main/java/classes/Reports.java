package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class Reports {
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
    public static void reportsMenu(){
        boolean running = true;

        LocalDate today = LocalDate.now();
        while(running) {
            List<Transaction> transactions = TransactionHandler.getTransactions();
            System.out.println(cyan +"*********************************REPORTS************************************"+reset);
            System.out.println("1. Month To Date Transactions");
            System.out.println("2. Previous Month Transactions");
            System.out.println("3. Year To Date Transactions");
            System.out.println("4. Previous Year Transactions");
            System.out.println("5. Search by Vendor");
            System.out.println("6. Custom Search");
            System.out.println("0. Back");
            String choice = scanner.nextLine();
            int txnCntThisMonth=0;
            int txnCntLastMonth=0;
            int txnCntThisYear=0;
            int txnCntLastYear=0;
            switch (choice) {

                case "1":  //month to date transactions

                    LocalDate startOfMonth = today.withDayOfMonth(1);
                    List<Transaction> monthToDateTransactions = new ArrayList<>();
                    // go through all transactions this month and if the date is in the current month add them to the month to date transaction list
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();

                        if ((date.isAfter(startOfMonth) || date.isEqual(startOfMonth)) && (date.isBefore(today) || date.isEqual(today))) {
                            monthToDateTransactions.add(transaction);
                            txnCntThisMonth++;
                        }
                    }
                    System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    //Print the month to date transaction list
                    for (Transaction transaction : monthToDateTransactions) {
                        // if its a sale(deposit) print in green, and if its a payment print in red
                        if(transaction.amount>0) {
                            System.out.println(green+transaction+reset);
                        }
                        else
                            System.out.println(red+transaction+reset);
                    }
                    //display number of transactions
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    System.out.println(purple +txnCntThisMonth + " transactions in total\n\n\n\n\n"+reset);
                    break;
                case "2":  // last month transactions
                    LocalDate startPrevM = today.minusMonths(1).withDayOfMonth(1);
                    LocalDate endPrevM = startPrevM.withDayOfMonth(startPrevM.lengthOfMonth());
                    List<Transaction> previousMonthTransactions = new ArrayList<>();
                    // go through all out transactions and add them to last month transaction list if date is in the last month
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startPrevM) && !date.isAfter(endPrevM)) {
                            previousMonthTransactions.add(transaction);
                            txnCntLastMonth++;
                        }
                    }
                    System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    for (Transaction transaction : previousMonthTransactions) {
                        // if its a sale(deposit) print in green, and if its a payment print in red
                        if(transaction.amount>0) {
                            System.out.println(green+transaction+reset);
                        }
                        else
                            System.out.println(red+transaction+reset);
                    }

                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    System.out.println(purple +txnCntLastMonth + " transactions in total\n\n\n\n\n"+reset);
                    break;
                case "3":   // year to date transactions
                    LocalDate startOfYear = today.withDayOfYear(1);
                    List<Transaction> yearToDateTransactions = new ArrayList<>();
                    // go through all transactions and add them to our year to date transaction list if the date is in the current year
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startOfYear) && !date.isAfter(today)) {
                            yearToDateTransactions.add(transaction);
                            txnCntThisYear++;
                        }
                    }
                    System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    // print out year to date transaction list
                    for (Transaction transaction : yearToDateTransactions) {
                        // if its a sale(deposit) print in green, and if its a payment print in red
                        if(transaction.amount>0) {
                            System.out.println(green+transaction+reset);
                        }
                        else
                            System.out.println(red+transaction+reset);
                    }
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    System.out.println(purple +txnCntThisYear + " transactions in total\n\n\n\n\n"+reset);
                    break;
                case "4":   //Last year Transactions

                    LocalDate startOfPrevYear = today.minusYears(1).withDayOfYear(1);
                    LocalDate endOfPrevYear = startOfPrevYear.withDayOfYear(startOfPrevYear.lengthOfYear());

                    List<Transaction> previousYearTransactions = new ArrayList<>();
                    //go through all transactions and them to our previous year transactions list if the date is in the last year

                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startOfPrevYear) && !date.isAfter(endOfPrevYear)) {  // This checks if the date is within the last year
                            previousYearTransactions.add(transaction);
                            txnCntLastYear++;
                        }
                    }
                    System.out.println(cyan + "    DATE  |  TIME  |DESCRIPTION| VENDOR | AMOUNT"+ reset);
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    // print out previous year transactio list
                    for (Transaction transaction : previousYearTransactions) {
                        // if its a sale(deposit) print in green, and if it's a payment print in red
                        if(transaction.amount>0) {
                            System.out.println(green+transaction+reset);
                        }
                        else
                            System.out.println(red+transaction+reset);
                    }

                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    System.out.println(purple +txnCntLastYear + " transactions in total\n\n\n\n\n"+reset);
                    break;


                case "5":
                    boolean validVendor =false;
                    String vendorName ="";
                    while (!validVendor) { // loops until value is valid
                        System.out.println("Enter vendor name to search");

                        try {
                           vendorName = scanner.nextLine().trim();
                           // make the user enter at least 3 characters for an effective search
                          if(vendorName.length()<3){
                              System.out.println(bold+purple+"please enter at least 3 characters to search "+reset);
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
                case "6":
                    CustomSearch.customSearchMenu();
                    break;

                case "0":
                    running =false;
                    break;
                default:
                    System.out.println(bold+red+"wrong input try again");
                    break;
            }
        }

    }



}
