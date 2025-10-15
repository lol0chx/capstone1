package classes;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import static classes.Ledger.LedgerMenu;
import static classes.TransactionHandler.TakeAPayment;
import static classes.TransactionHandler.displayTransactions;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HOME {
    static Scanner scanner = new Scanner(System.in);
    static List<Transaction> Transactions = TransactionHandler.LoadTransactions();
    static void main() {

        MainMenu();
        //TransactionHandler.displayTransactions(Transactions);



    }
    public static void MainMenu(){
        boolean running = true;
        while(running) {
        System.out.println("*********************************WELLCOME************************************");
        System.out.println("1. TAKE A PAYMENT (DEPOSIT)");
        System.out.println("2. MAKE A PAYMENT");
        System.out.println("3. LEDGER");
        System.out.println("4.EXIT");
        System.out.println("choose an option:");
        int choice = scanner.nextInt();
        scanner.nextLine();


            switch (choice) {
                case 1:
                    TransactionHandler.TakeAPayment();
                    break;
                case 2:
                    TransactionHandler.MakeAPayment();
                    break;
                case 3:
                    Ledger.LedgerMenu();
                    break;
                case 4:
                    System.out.println("BYE!");
                    running = false;
                    break;
                default:
                    System.out.println("Invalid choice. Try again.");

            }
        }
    }
}