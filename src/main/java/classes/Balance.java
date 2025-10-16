package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    // get the loaded transactions from Transaction handler
    static List<Transaction> transactions = TransactionHandler.getTransactions();
    public static void balanceSummary(){

        double currentBalance=0;
        double moneySpentThisMonth =0;
        double moneySpentLastMonth =0;
        double moneySpentThisYear= 0;
        double moneySpentLastYear=0;

        // Go through all transactions and just add them up to get current balance
        for(Transaction transaction: transactions){
            currentBalance = currentBalance+ transaction.getAmount();
        }
        // print current balance
        System.out.printf("Balance: %.3f",currentBalance);

        LocalDate today = LocalDate.now();

        LocalDate startOfMonth = today.withDayOfMonth(1);   // get the start of the month

        // go through all amounts this month and add the negative amounts only  to get the amount spent this month
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if ((date.isAfter(startOfMonth) || date.isEqual(startOfMonth)) && (date.isBefore(today) || date.isEqual(today))) { // this check if the date is in this month
                if(transaction.getAmount()<0){
                    moneySpentThisMonth+=transaction.getAmount(); //this adds the amount
                }

            }
        }

        //print the money spent this month
        System.out.printf("\nmoney spent this month: %.3f",moneySpentThisMonth);

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
            }
        }

        //print the money spent last month
        System.out.printf("\nmoney spent Last month: %.3f",moneySpentLastMonth);




        LocalDate startOfYear = today.withDayOfYear(1);  // get the start of the year

        // go through all our transactions this year and add the negative amounts to get the money spent this year
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if (!date.isBefore(startOfYear) && !date.isAfter(today)) {  // this checks if the date is in this year
                if(transaction.getAmount()<0){
                    moneySpentThisYear+=transaction.getAmount();  // this adds the amount
                }

            }
        }

        // print the money spent this year
        System.out.printf("\nmoney spent This year: %.3f",moneySpentThisYear);



        // get the start and end of the previous year
        LocalDate startOfPrevYear = today.minusYears(1).withDayOfYear(1);
        LocalDate endOfPrevYear = startOfPrevYear.withDayOfYear(startOfPrevYear.lengthOfYear());


        //go through all transactions last year and add the negative amounts to get the money spent last year
        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            if (!date.isBefore(startOfPrevYear) && !date.isAfter(endOfPrevYear)) {  // This checks if the date is in the last year
                if(transaction.getAmount()<0){
                    moneySpentLastYear+=transaction.getAmount();  //this adds the amount
                }
            }
        }
        // print the money spent last year
        System.out.printf("\nmoney spent Last year: %.3f",moneySpentLastYear);

        }
    }

