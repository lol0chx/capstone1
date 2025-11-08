package gui;

import classes.Transaction;
import classes.TransactionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class ReportsPanel extends JPanel {
    private MainGUI mainGUI;
    private JTable reportTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> reportTypeCombo;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public ReportsPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Reports");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Controls panel
        JPanel controlsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controlsPanel.add(new JLabel("Report Type:"));
        reportTypeCombo = new JComboBox<>(new String[]{
            "Month To Date",
            "Previous Month",
            "Year To Date",
            "Previous Year"
        });
        reportTypeCombo.addActionListener(e -> generateReport());
        controlsPanel.add(reportTypeCombo);

        JButton generateButton = new JButton("Generate Report");
        generateButton.addActionListener(e -> generateReport());
        controlsPanel.add(generateButton);

        // Table setup
        String[] columnNames = {"Date", "Time", "Description", "Vendor", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        reportTable = new JTable(tableModel);
        reportTable.setAutoCreateRowSorter(true);

        // Custom renderer for amount column
        reportTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (value != null) {
                    double amount = (Double) value;
                    setText(String.format("$%.2f", amount));
                    if (!isSelected) {
                        setForeground(amount >= 0 ? new Color(0, 150, 0) : Color.RED);
                    }
                }
                return c;
            }
        });

        // Summary panel
        JPanel summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        summaryPanel.setBorder(BorderFactory.createTitledBorder("Summary"));

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(controlsPanel, BorderLayout.NORTH);
        centerPanel.add(new JScrollPane(reportTable), BorderLayout.CENTER);
        centerPanel.add(summaryPanel, BorderLayout.SOUTH);
        add(centerPanel, BorderLayout.CENTER);

        // Don't generate report automatically
        // We'll wait for user to click the Generate Report button
    }

    private void generateReport() {
        tableModel.setRowCount(0);
        List<Transaction> transactions = TransactionHandler.getTransactions();
        List<Transaction> filteredTransactions = new ArrayList<>();
        
        LocalDate today = LocalDate.now();
        LocalDate startDate = null;
        LocalDate endDate = today;

        String reportType = (String) reportTypeCombo.getSelectedItem();
        switch (reportType) {
            case "Month To Date":
                startDate = today.withDayOfMonth(1);
                break;
            case "Previous Month":
                startDate = today.minusMonths(1).withDayOfMonth(1);
                endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
                break;
            case "Year To Date":
                startDate = today.withDayOfYear(1);
                break;
            case "Previous Year":
                startDate = today.minusYears(1).withDayOfYear(1);
                endDate = startDate.withDayOfYear(startDate.lengthOfYear());
                break;
        }

        // Filter transactions based on date range
        for (Transaction transaction : transactions) {
            LocalDate transactionDate = transaction.getDate();
            if (!transactionDate.isBefore(startDate) && !transactionDate.isAfter(endDate)) {
                filteredTransactions.add(transaction);
            }
        }

        // Update table with filtered transactions
        for (Transaction transaction : filteredTransactions) {
            tableModel.addRow(new Object[]{
                transaction.getDate().format(dateFormatter),
                transaction.getTime().format(timeFormatter),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount()
            });
        }

        // Calculate and display summary
        calculateAndDisplaySummary(filteredTransactions);
    }

    private void calculateAndDisplaySummary(List<Transaction> transactions) {
        double totalIncome = 0;
        double totalExpenses = 0;

        for (Transaction transaction : transactions) {
            double amount = transaction.getAmount();
            if (amount > 0) {
                totalIncome += amount;
            } else {
                totalExpenses += Math.abs(amount);
            }
        }

        double netProfit = totalIncome - totalExpenses;

        String summary = String.format(
            "<html>Total Income: <font color='green'>$%.2f</font><br>" +
            "Total Expenses: <font color='red'>$%.2f</font><br>" +
            "Net Profit: <font color='%s'>$%.2f</font></html>",
            totalIncome, totalExpenses,
            netProfit >= 0 ? "green" : "red", netProfit
        );

        JOptionPane.showMessageDialog(this, summary, "Report Summary", JOptionPane.INFORMATION_MESSAGE);
    }
}