package classes;

import java.util.ArrayList;
import java.util.List;

import static classes.TransactionHandler.displayTransactions;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class HOME {
    static List<Transaction> Transactions = TransactionHandler.LoadTransactions();
    static void main() {
        //static List<Transaction> Transactions = TransactionHandler.LoadTransactions();
        System.out.println("TEST");
       // displayTransactions(Transactions);
        //List<Transaction> Transactions = new ArrayList<>();
        TransactionHandler.LoadTransactions();
        TransactionHandler.displayTransactions(Transactions);
    }
}