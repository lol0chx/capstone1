package classes;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Reports {
    static Scanner scanner = new Scanner(System.in);

    public static void ReportsMenu(){
        List<Transaction> transactions= TransactionHandler.getTransactions();
        System.out.println("*********Reports*********");
        System.out.println("1. Month To Date");
        System.out.println("2. Previous Month");
        System.out.println("3. Year To Date");
        System.out.println("4. Previous Year");
        System.out.println("5. Search by Vendor");
        System.out.println("0. Back");
        int choice =scanner.nextInt();
        switch (choice){
            case 1:
                LocalDate today = LocalDate.now();
                LocalDate StartofMonth = today.withDayOfMonth(1);
                List<Transaction> MtD = new ArrayList<>();
                for(Transaction transaction: transactions){
                    LocalDate date = transaction.getDate();
                    if(!date.isBefore(StartofMonth)&& !date.isBefore(today)){
                        MtD.add(transaction);
                    }
                }
                for (Transaction transaction: transactions){
                    System.out.println("see");
                    System.out.println(MtD);
                }
        }
    }
}
