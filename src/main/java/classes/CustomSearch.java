package classes;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomSearch {
    static Scanner scanner = new Scanner(System.in);
    //static List<Transaction> transactions = TransactionHandler.LoadTransactions();

    public static void customSearchMenu(){
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
        boolean running = true;
        while(running) {
            System.out.println("*********************************Custom Search ************************************");
            List<Transaction> transactions = TransactionHandler.getTransactions();
            System.out.println("1. Search by Start Date.");
            System.out.println("2. Search by End Date");
            System.out.println("3. Description");
            System.out.println("4. Amount");
            System.out.println("0. Go back");
            String choice = scanner.nextLine();
            scanner.nextLine();

            switch (choice) {
                case "1":
                    System.out.println("Enter start date in this format(MM/dd/yyyy)");
                    String startDateInput = scanner.nextLine();
                    LocalDate filteredStartDate= null;

                    try{
                        filteredStartDate = LocalDate.parse(startDateInput,dateFormatter);
                    }catch (DateTimeException e){
                        System.out.println("Invalid date format");
                        return;
                    }

                    List<Transaction> startDateFilteredList = new ArrayList<>();
                    for( Transaction transaction: transactions){
                        if(transaction.getDate().isAfter(filteredStartDate)){
                            startDateFilteredList.add(transaction);
                        }
                    }
                    if (startDateFilteredList.isEmpty()){
                        System.out.println("There are no transactions after that date");
                    }else {
                        System.out.println("Transactions after "+ filteredStartDate+"\n");
                        for (Transaction transaction : startDateFilteredList) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "2":
                    System.out.println("Enter start date in this format(MM/dd/yyyy)");
                    String EndDateInput = scanner.nextLine();
                    LocalDate filteredEndDate= null;
                   // DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    try{
                        filteredEndDate = LocalDate.parse(EndDateInput,dateFormatter);
                    }catch (DateTimeException e){
                        System.out.println("Invalid date format");
                        return;
                    }

                    List<Transaction> endDateFilteredList = new ArrayList<>();
                    for( Transaction transaction: transactions){
                        if(transaction.getDate().isBefore(filteredEndDate)){
                            endDateFilteredList.add(transaction);
                        }
                    }
                    if (endDateFilteredList.isEmpty()){
                        System.out.println("There are no transactions before that date");
                    }else {
                        System.out.println("Transactions before  "+ filteredEndDate+"\n");
                        for (Transaction transaction : endDateFilteredList) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "3":
                    System.out.println("Enter description to search");
                    String description = scanner.nextLine();
                    List<Transaction> descriptionFilteredList = new ArrayList<>();
                    for (Transaction transaction : transactions) {
                        if (transaction.getDescription().contains(description)) {
                            descriptionFilteredList.add(transaction);

                        }
                    }
                    if(descriptionFilteredList.isEmpty()){
                        System.out.println("there is no transaction with that description");
                    }
                    else {
                        for (Transaction transaction : descriptionFilteredList) {
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "4":
                    System.out.println("Enter amount");
                    double amountinput = scanner.nextDouble();
                    scanner.nextLine();
                    List<Transaction> amountFilteredList = new ArrayList<>();
                    for(Transaction transaction: transactions)
                    {
                        if(transaction.getAmount()>=amountinput){
                            amountFilteredList.add(transaction);
                        }
                    }
                    if(amountFilteredList.isEmpty()){
                        System.out.println("there is no transaction with that amount ");
                    }
                    else {
                        for(Transaction transaction: amountFilteredList){
                            System.out.println(transaction);
                        }
                    }
                    break;
                case "0":
                    running=false;
                default:
                    System.out.println("Invalid Choice try again:");
                    break;


            }
        }
    }
}
