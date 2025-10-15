package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reports {
    static Scanner scanner = new Scanner(System.in);

    public static void reportsMenu(){
        boolean running = true;
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
            int choice = scanner.nextInt();
            scanner.nextLine();
            switch (choice) {
                case 1:

                    LocalDate today = LocalDate.now();
                   // System.out.println("today is " + today + "\n");
                    LocalDate startofMonth = today.withDayOfMonth(1);
                    // System.out.println("start of month is "+startofMonth);
                    List<Transaction> MtDTransactions = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        // System.out.println("transaction date"+ date);
                        if ((date.isAfter(startofMonth) || date.isEqual(startofMonth)) && (date.isBefore(today) || date.isEqual(today))) {
                            MtDTransactions.add(transaction);
                        }
                    }
                    for (Transaction transaction : MtDTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 2:
                    LocalDate today1 = LocalDate.now();
                    LocalDate startPrevM = today1.minusMonths(1).withDayOfMonth(1);
                    LocalDate endPrevM = startPrevM.withDayOfMonth(startPrevM.lengthOfMonth());
                    List<Transaction> PreMoTransactions = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startPrevM) && !date.isAfter(endPrevM)) {
                            PreMoTransactions.add(transaction);
                        }
                    }
                    for (Transaction transaction : PreMoTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 3:
                    LocalDate today2 = LocalDate.now();
                    LocalDate startOfYear = today2.withDayOfYear(1);
                    List<Transaction> yearToDateTransactions = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startOfYear) && !date.isAfter(today2)) {
                            yearToDateTransactions.add(transaction);
                        }
                    }
                    for (Transaction transaction : yearToDateTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 4:
                    LocalDate today3 = LocalDate.now();
                    LocalDate startOfPrevYear = today3.minusYears(1).withDayOfYear(1);
                    LocalDate endOfPrevYear = startOfPrevYear.withDayOfYear(startOfPrevYear.lengthOfYear());
                    List<Transaction> prevYearTransactions = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        LocalDate date = transaction.getDate();
                        if (!date.isBefore(startOfPrevYear) && !date.isAfter(endOfPrevYear)) {
                            prevYearTransactions.add(transaction);

                        }
                    }
                    for (Transaction transaction : prevYearTransactions) {
                        System.out.println(transaction);
                    }
                    break;
                case 5:
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
                case 6:
                    CustomSearch.customSearchMenu();
                    break;

                case 0:
                    running =false;
                    break;
                default:
                    System.out.println("wrong input");
                    break;
            }
        }

    }
}
