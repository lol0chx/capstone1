package gui;

import classes.Transaction;
import classes.TransactionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class LedgerPanel extends JPanel {
    private MainGUI mainGUI;
    private JTable transactionTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> filterComboBox;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public LedgerPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Transaction Ledger");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Filter panel
        JPanel filterPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        filterPanel.add(new JLabel("Show:"));
        filterComboBox = new JComboBox<>(new String[]{"All Transactions", "Sales Only", "Purchases Only"});
        filterComboBox.addActionListener(e -> updateTable());
        filterPanel.add(filterComboBox);

        // Table
        String[] columnNames = {"Date", "Time", "Description", "Vendor", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // Make table read-only
            }
        };
        transactionTable = new JTable(tableModel);
        transactionTable.setAutoCreateRowSorter(true);

        // Custom renderer for amount column to show colors and currency format
        transactionTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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

        // Scroll pane for table
        JScrollPane scrollPane = new JScrollPane(transactionTable);
        
        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateTable());
        buttonPanel.add(refreshButton);

        // Layout
        add(titlePanel, BorderLayout.NORTH);
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.add(filterPanel, BorderLayout.NORTH);
        centerPanel.add(scrollPane, BorderLayout.CENTER);
        add(centerPanel, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        // Initial table load
        updateTable();
    }

    private void updateTable() {
        tableModel.setRowCount(0); // Clear existing rows
        List<Transaction> transactions = TransactionHandler.getTransactions();
        String filter = (String) filterComboBox.getSelectedItem();

        for (Transaction transaction : transactions) {
            // Apply filter
            if (filter.equals("Sales Only") && transaction.getAmount() < 0) continue;
            if (filter.equals("Purchases Only") && transaction.getAmount() > 0) continue;

            tableModel.addRow(new Object[]{
                transaction.getDate().format(dateFormatter),
                transaction.getTime().format(timeFormatter),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount()
            });
        }
    }
}