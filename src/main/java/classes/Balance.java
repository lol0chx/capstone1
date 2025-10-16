package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Balance { // define ANSI color codes
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String white = "\u001B[37m";
    // get the loaded transactions from Transaction handler
    static List<Transaction> transactions = TransactionHandler.getTransactions();
    public static void balanceSummary(){

        double currentBalance=0;
        double moneySpentThisMonth =0;
        double moneyEarnedThisMonth=0;
        double netProfitThisMonth=0;
        double moneySpentLastMonth =0;
        double moneyEarnedLastMonth=0;
        double netProfitLastMonth=0;
        double moneySpentThisYear= 0;
        double moneyEarnedThisYear =0;
        double netProfitThisYear =0;
        double moneySpentLastYear=0;
        double moneyEarnedLastYear=0;
        double netProfitLastYear=0;


        // Go through all transactions and just add them up to get current balance
        for(Transaction transaction: transactions){
            currentBalance = currentBalance+ transaction.getAmount();
        }
        // print current balance
        System.out.printf("Balance: %.3f",currentBalance);

        LocalDate today = LocalDate.now();

        LocalDate startOfMonth = today.withDayOfMonth(1);   // get the start of the month

        // go through all the transactions this month
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if ((date.isAfter(startOfMonth) || date.isEqual(startOfMonth)) && (date.isBefore(today) || date.isEqual(today))) { // this check if the date is in this month
                if(transaction.getAmount()<0){
                    moneySpentThisMonth+=transaction.getAmount(); //adds up the amount of money spent

                }
                if(transaction.getAmount()>0){
                    moneyEarnedThisMonth+=transaction.getAmount(); // add up the amount of money earned
                }

            }
        }
        netProfitThisMonth= moneyEarnedThisMonth+moneySpentThisMonth; // calculate net profit of the month

        System.out.printf("\nmoney spent this month: %.3f"+red,moneySpentThisMonth);//print the money spent this month
        System.out.println(reset);
        System.out.printf("\nmoney earned this month: %.3f"+green,moneyEarnedThisMonth); // print the money earned this month
        System.out.println(reset);
        System.out.printf("\nNet Profit this month : %.3f",netProfitThisMonth);//print net profit of this month


        //get the start and end of previous month days
        LocalDate startPrevMonth = today.minusMonths(1).withDayOfMonth(1);
        LocalDate endPrevMonth = startPrevMonth.withDayOfMonth(startPrevMonth.lengthOfMonth());

       //go through all the transactions last month and add the negative amounts to get the money spent last month
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if (!date.isBefore(startPrevMonth) && !date.isAfter(endPrevMonth)) { // this checks if the date is in the last month
                if(transaction.getAmount()<0){
                    moneySpentLastMonth+=transaction.getAmount(); // this adds the amount
                }
                if(transaction.getAmount()>0){
                    moneyEarnedLastMonth+=transaction.getAmount();
                }
            }
        }
         netProfitLastMonth=moneyEarnedLastMonth+moneySpentLastMonth;
        //print the money spent last month
        System.out.printf("\nmoney spent Last month: %.3f",moneySpentLastMonth);
        System.out.printf("\nmoney earned Last month: %.3f",moneyEarnedLastMonth); // print the money Last month
        System.out.printf("\nNet Profit Last month : %.3f",netProfitLastMonth);//print net profit of this month



        LocalDate startOfYear = today.withDayOfYear(1);  // get the start of the year

        // go through all our transactions this year
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if (!date.isBefore(startOfYear) && !date.isAfter(today)) {  // this checks if the date is in this year
                if(transaction.getAmount()<0){
                    moneySpentThisYear+=transaction.getAmount();  // this adds the amount of money spent this year
                }
                if(transaction.getAmount()>0){
                    moneyEarnedThisYear+= transaction.getAmount();// this adds the amount of money earned this year
                }
            }
        }
        netProfitThisYear=moneyEarnedThisYear+moneySpentThisYear;

        // print the money spent this year
        System.out.printf("\nmoney spent This year: %.3f",moneySpentThisYear);//print the money spent this year
        System.out.printf("\nmoney earned this year: %.3f",moneyEarnedThisYear); // print the money earned this year
        System.out.printf("\nNet Profit this year : %.3f",netProfitThisYear);//print net profit of this year


        // get the start and end of the previous year
        LocalDate startOfPrevYear = today.minusYears(1).withDayOfYear(1);
        LocalDate endOfPrevYear = startOfPrevYear.withDayOfYear(startOfPrevYear.lengthOfYear());


        //go through all transactions last year and add the negative amounts to get the money spent last year
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if (!date.isBefore(startOfPrevYear) && !date.isAfter(endOfPrevYear)) {  // This checks if the date is in the last year
                if(transaction.getAmount()<0){
                    moneySpentLastYear+=transaction.getAmount();  //this adds the amount of money spent last year
                }
                if(transaction.getAmount()>0){
                    moneyEarnedLastYear+= transaction.getAmount();  //adds the amount of money earned last year
                }
            }
        }

        netProfitLastYear = moneySpentLastYear+moneyEarnedLastYear;
        // print the money spent last year
        System.out.printf("\nmoney spent Last year: %.3f",moneySpentLastYear);
        System.out.printf("\nmoney earned last year: %.3f",moneyEarnedLastYear); // print the money earned this year
        System.out.printf("\nNet Profit last year : %.3f",netProfitLastYear);//print net profit of this year
        }
    }

