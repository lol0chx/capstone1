package classes;

import java.util.List;
import java.util.Scanner;


public class Home {

    // define ANSI color codes
    public static final String reset = "\u001B[0m";
    public static final String red = "\u001B[31m";
    public static final String green = "\u001B[32m";
    public static final String yellow = "\u001B[33m";
    public static final String blue = "\u001B[34m";
    public static final String purple = "\u001B[35m";
    public static final String cyan = "\u001B[36m";
    public static final String bold = "\u001B[1m";
    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> Transactions = TransactionHandler.LoadTransactions(); // Load all our transactions
    static void main() {

        MainMenu();

    }
    public static void MainMenu(){
        boolean running = true;
        while(running) {  // this keeps the program running unlit we press exit
            System.out.println(cyan + "\n====================================================================" + reset);
            System.out.println(yellow + "🤑  L E - F L I P   —   Buy • Sell • Profit" + reset);
            System.out.println(cyan + "====================================================================" + reset);
            System.out.println(green + "Welcome to FlipTrack! Your personal reseller assistant." + reset);
            System.out.println();
            System.out.println("Track all your buys, sales, and profits from Anywhere" + reset);
            System.out.println(cyan + "====================================================================" + reset);
        System.out.println("1. Add sale (Sell Item)");
        System.out.println("2. Add Purchase (Buy Item)");
        System.out.println("3. Ledger");
        System.out.println("4. Balance Summary");
        System.out.println("0.EXIT");
        System.out.println("choose an option:");
        String choice = scanner.nextLine();


            switch (choice) {
                case "1":
                    System.out.println("Type"+purple+" exit "+reset +"for vendor or description to cancel transaction");
                    TransactionHandler.takePayment();
                    break;
                case "2":
                    System.out.println("Type"+purple+" exit "+reset +"for vendor or description to cancel transaction");
                    TransactionHandler.makePayment();
                    break;
                case "3":
                    Ledger.ledgerMenu();
                    break;
                case "4":
                  Balance.balanceSummary();

                  break;
                case "0":
                    System.out.println("BYE!");
                    running = false;
                    break;
                default:
                    System.out.println(bold+red +"Invalid choice. Try again."+reset);

            }
        }
    }
}