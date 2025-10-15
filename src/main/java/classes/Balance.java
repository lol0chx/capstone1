package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Balance {
    static List<Transaction> transactions = TransactionHandler.getTransactions();
    public static void balanceSummary(){
        double currentBalance=0;
        double moneySpentThisMonth =0;

        // Go through all transactions and just add them up to get current balance
        for(Transaction transaction: transactions){
            currentBalance = currentBalance+ transaction.getAmount();
        }
        System.out.printf("Balance: %.3f",currentBalance);

        LocalDate today = LocalDate.now();

        LocalDate startofMonth = today.withDayOfMonth(1);

        for (Transaction transaction : transactions) {
            LocalDate date = transaction.getDate();
            // go through all amounts this month and add them
            if ((date.isAfter(startofMonth) || date.isEqual(startofMonth)) && (date.isBefore(today) || date.isEqual(today))) {
                moneySpentThisMonth=moneySpentThisMonth+transaction.getAmount();
            }
        }

        System.out.printf("\nmoney spent this month: %.3f",moneySpentThisMonth);


        }
    }

