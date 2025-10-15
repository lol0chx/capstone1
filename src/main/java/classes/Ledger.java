package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    static Scanner scanner = new Scanner(System.in);
    //static List<Transaction> transactions = TransactionHandler.LoadTransactions();

    public static void ledgerMenu(){
        boolean running = true;
        while(running) {
            System.out.println("*********************************LEDGER MENU************************************");
        List<Transaction> transactions = TransactionHandler.getTransactions();
        System.out.println("1.Display ALl Transactions");
        System.out.println("2.Display Deposits only");
        System.out.println("3.Display Payments Made only");
        System.out.println("4.Reports");
        System.out.println("5.Go back to Main Menu");
        int choice = scanner.nextInt();
        scanner.nextLine();

            switch (choice) {
                case 1:
                    TransactionHandler.displayTransactions(transactions);
                    break;
                case 2:
                    List<Transaction> DepositTransaction = new ArrayList<>();
                    for (Transaction depositTransactions : transactions) {

                        if (depositTransactions.getAmount() > 0) {
                            DepositTransaction.add(depositTransactions);
                        }
                    }
                    for (Transaction dtransactions : DepositTransaction) {
                        System.out.println(dtransactions);
                    }
                    break;
                case 3:
                    List<Transaction> PaymentsTransaction = new ArrayList<>();
                    for (Transaction paymentTransactions : transactions) {

                        if (paymentTransactions.getAmount() < 0) {
                            PaymentsTransaction.add(paymentTransactions);
                        }
                    }
                    for (Transaction Dtransactions : PaymentsTransaction) {
                        System.out.println(Dtransactions);
                    }
                    break;
                case 4:
                    Reports.reportsMenu();
                    break;
                case 5:
                    running =false;

            }
        }
    }
}
