package classes;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class TransactionHandler {
    static public List<Transaction> productLists = new ArrayList<>();

    public static List<Transaction> LoadTransactions(){
        List<Transaction> transactions = new ArrayList<>();
        try {
           // FileReader fileReader = new FileReader("transactions.csv");
           // BufferedReader bufReader = new BufferedReader(fileReader);
            BufferedReader bufReader = new BufferedReader (new FileReader("transactions.csv"));
            bufReader.readLine();
            String productsRead;
            while ((productsRead = bufReader.readLine()) != null) {
                String[] parts = productsRead.split("\\|");
                if(parts.length==4) {
                    Transaction transaction = new Transaction( LocalDate.parse(parts[0]), LocalTime.parse(parts[1]), parts[2], parts[3], Double.parseDouble(parts[4]) ) ;


                }
            }

            //Employee employee = new Employee(Integer.parseInt(parts[0]), parts[1], Double.parseDouble(parts[2]), Double.parseDouble(parts[3]));
            // System.out.printf("The employee %s with id %d pay is $%.2f\n", employee.getName(), employee.getEmployeeid(), employee.getGrossPay());
            bufReader.close();
        } catch (IOException e) {
            // display stack trace if there was an error
            System.out.println("\nError reading file");
            e.printStackTrace();
        }
        return transactions;
    }

}
