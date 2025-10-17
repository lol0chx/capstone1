package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Ledger {
    // define ANSI color codes
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

    public static void ledgerMenu(){

        boolean running = true;
        while(running) {

            System.out.println(cyan + "*********************************LEDGER************************************"+ reset);
        List<Transaction> transactions = TransactionHandler.getTransactions();
        System.out.println("1.Display ALl Transactions");
        System.out.println("2.Display Deposits ");
        System.out.println("3.Display Payments Made ");
        System.out.println("4.Reports");
        System.out.println("0.Go back to Main Menu");
        String choice = scanner.nextLine();
            int numberOfDepositTransactions=0;
            int numberOfPaymentTransactions=0;
            switch (choice) {

                // just call display transaction from transaction handler class
                case "1":
                    TransactionHandler.displayTransactions(transactions);
                    break;
                case "2":
                    //go through all our transactions and add them to deposit transaction list if its a positive amount
                    List<Transaction> DepositTransaction = new ArrayList<>();
                    for (Transaction depositTransactions : transactions) {
                        if (depositTransactions.getAmount() > 0) {  // check if its positive
                            DepositTransaction.add(depositTransactions);
                            numberOfDepositTransactions++;
                        }
                    }
                    // display all deposit transactions
                    for (Transaction depositTransactions : DepositTransaction) {
                        System.out.println(green +depositTransactions+reset);
                    }
                    //display total number of transactions in the list
                    System.out.println(cyan + "-------------------------------------------------------------------------"+reset);
                    System.out.println(green +numberOfDepositTransactions + " transactions in total\n\n\n\n\n"+reset);
                    break;
                case "3":
                    // go through all transactions and add them to payments transaction list if its negative
                    List<Transaction> PaymentsTransaction = new ArrayList<>();
                    for (Transaction paymentTransactions : transactions) {
                       // check if its negative
                        if (paymentTransactions.getAmount() < 0) {
                            PaymentsTransaction.add(paymentTransactions);
                            numberOfPaymentTransactions++; //count number of transactions
                        }

                    }
                    for (Transaction paymentTransaction : PaymentsTransaction) {
                        System.out.println(red+paymentTransaction+reset);
                    }
                    System.out.println(cyan + "--------------------------------------------------------------------------"+reset);
                    System.out.println(red +numberOfPaymentTransactions + " transactions in total\n\n\n\n\n"+reset);
                    break;
                case "4":
                    Reports.reportsMenu();
                    break;
                case "0":
                    running =false;
                    break;
                default:
                    System.out.println(red +bold+"Invalid choice. Try again."+ reset);
                    break;

            }
        }
    }

}
