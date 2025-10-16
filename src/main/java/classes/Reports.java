package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reports {
    static Scanner scanner = new Scanner(System.in);
    public static void reportsMenu(){
        boolean running = true;

        LocalDate today = LocalDate.now();
        while(running) {
            List<Transaction> transactions = TransactionHandler.getTransactions();
            System.out.println("*********************************REPORTS************************************");
            System.out.println("1. Month To Date Transactions");
            System.out.println("2. Previous Month Transactions");
            System.out.println("3. Year To Date Transactions");
            System.out.println("4. Previous Year Transactions");
            System.out.println("5. Search by Vendor");
            System.out.println("6. Custom Search");
            System.out.println("0. Back");
            String choice = scanner.nextLine();

            switch (choice) {

                case "1":  //month to date transactions

                    LocalDate startOfMonth = today.withDayOfMonth(1);
                    List<Transaction> monthToDateTransactions = new ArrayList<>();
                    // go through all transactions this month and if the date is in the current month add them to the month to date transaction list
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();

                        if ((date.isAfter(startOfMonth) || date.isEqual(startOfMonth)) && (date.isBefore(today) || date.isEqual(today))) {
                            monthToDateTransactions.add(transaction);
                        }
                    }
                    //Print the month to date transaction list
                    for (Transaction transaction : monthToDateTransactions) {
                        System.out.println(transaction);
                    }
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
                        }
                    }

                    // print the previous month transactions list
                    for (Transaction transaction : previousMonthTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case "3":   // year to date transactions
                    LocalDate startOfYear = today.withDayOfYear(1);
                    List<Transaction> yearToDateTransactions = new ArrayList<>();
                    // go through all transactions and add them to our year to date transaction list if the date is in the current year
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startOfYear) && !date.isAfter(today)) {
                            yearToDateTransactions.add(transaction);

                        }
                    }
                    // print out year to date transaction list
                    for (Transaction transaction : yearToDateTransactions) {
                        System.out.println(transaction);
                    }
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

                        }
                    }

                    // print out previous year transactio list
                    for (Transaction transaction : previousYearTransactions) {
                        System.out.println(transaction);
                    }
                    break;


                case "5":
                    System.out.println("Enter vendor name to search");
                    String vendorName = scanner.nextLine();
                    List<Transaction> vendorFilteredList = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        if (transaction.getVendor().equalsIgnoreCase(vendorName)) {
                            vendorFilteredList.add(transaction);

                        }
                    }
                    for (Transaction transaction : vendorFilteredList) {
                        System.out.println(transaction);
                    }
                    break;
                case "6":
                    CustomSearch.customSearchMenu();
                    break;

                case "0":
                    running =false;
                    break;
                default:
                    System.out.println("wrong input try again");
                    break;
            }
        }

    }



}
