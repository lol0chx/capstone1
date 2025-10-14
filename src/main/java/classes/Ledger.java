package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    static Scanner scanner = new Scanner(System.in);
    //static List<Transaction> transactions = TransactionHandler.LoadTransactions();

    public static void LedgerMenu(){
        List<Transaction> transactions = TransactionHandler.getTransactions();
        System.out.println("1.Display ALl Transactions");
        System.out.println("2.Display Deposits only");
        System.out.println("3.Display Payments Made only");
        System.out.println("4.Reports");
        System.out.println("5.Go back to Main Menu");
        int choice = scanner.nextInt();
        scanner.nextLine();
        switch (choice){
            case 1:
                TransactionHandler.displayTransactions(transactions);
                break;
            case 2:
                List<Transaction> DepositTransaction = new ArrayList<>();
                for(Transaction Dtransactions: transactions){

                    if( Dtransactions.getAmount()>0){
                        DepositTransaction.add(Dtransactions);
                    }
                }
                for(Transaction Dtransactions: DepositTransaction){
                    System.out.println(Dtransactions);
                }
                break;
            case 3:
                List<Transaction> PaymentsTransaction = new ArrayList<>();
                for(Transaction Dtransactions: transactions){

                    if( Dtransactions.getAmount()<0){
                        PaymentsTransaction.add(Dtransactions);
                    }
                }
                for(Transaction Dtransactions: PaymentsTransaction){
                    System.out.println(Dtransactions);
                }
                break;
            case 4:
                Reports.ReportsMenu();

        }
    }
}
