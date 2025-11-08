package gui;

import classes.Transaction;
import classes.TransactionHandler;
import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

public class CustomSearchPanel extends JPanel {
    private MainGUI mainGUI;
    private JTable resultTable;
    private DefaultTableModel tableModel;
    private JTextField startDateField;
    private JTextField endDateField;
    private JTextField descriptionField;
    private JTextField vendorField;
    private JTextField minAmountField;
    private JTextField maxAmountField;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public CustomSearchPanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Custom Search");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Search criteria panel
        JPanel searchPanel = new JPanel(new GridBagLayout());
        searchPanel.setBorder(BorderFactory.createTitledBorder("Search Criteria"));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Date range
        gbc.gridx = 0; gbc.gridy = 0;
        searchPanel.add(new JLabel("Start Date (MM/dd/yyyy):"), gbc);
        gbc.gridx = 1;
        startDateField = new JTextField(10);
        searchPanel.add(startDateField, gbc);

        gbc.gridx = 2;
        searchPanel.add(new JLabel("End Date (MM/dd/yyyy):"), gbc);
        gbc.gridx = 3;
        endDateField = new JTextField(10);
        searchPanel.add(endDateField, gbc);

        // Description and vendor
        gbc.gridx = 0; gbc.gridy = 1;
        searchPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(10);
        searchPanel.add(descriptionField, gbc);

        gbc.gridx = 2;
        searchPanel.add(new JLabel("Vendor:"), gbc);
        gbc.gridx = 3;
        vendorField = new JTextField(10);
        searchPanel.add(vendorField, gbc);

        // Amount range
        gbc.gridx = 0; gbc.gridy = 2;
        searchPanel.add(new JLabel("Min Amount:"), gbc);
        gbc.gridx = 1;
        minAmountField = new JTextField(10);
        searchPanel.add(minAmountField, gbc);

        gbc.gridx = 2;
        searchPanel.add(new JLabel("Max Amount:"), gbc);
        gbc.gridx = 3;
        maxAmountField = new JTextField(10);
        searchPanel.add(maxAmountField, gbc);

        // Search button
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 4;
        JButton searchButton = new JButton("Search");
        searchButton.addActionListener(e -> performSearch());
        searchPanel.add(searchButton, gbc);

        // Results table
        String[] columnNames = {"Date", "Time", "Description", "Vendor", "Amount"};
        tableModel = new DefaultTableModel(columnNames, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        resultTable = new JTable(tableModel);
        resultTable.setAutoCreateRowSorter(true);

        // Custom renderer for amount column
        resultTable.getColumnModel().getColumn(4).setCellRenderer(new DefaultTableCellRenderer() {
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

        // Add components to main panel
        add(titlePanel, BorderLayout.NORTH);
        add(searchPanel, BorderLayout.CENTER);
        add(new JScrollPane(resultTable), BorderLayout.SOUTH);
    }

    private void performSearch() {
        List<Transaction> allTransactions = TransactionHandler.getTransactions();
        List<Transaction> filteredTransactions = new ArrayList<>(allTransactions);

        // Date range filter
        try {
            if (!startDateField.getText().trim().isEmpty()) {
                LocalDate startDate = LocalDate.parse(startDateField.getText().trim(), dateFormatter);
                filteredTransactions.removeIf(t -> t.getDate().isBefore(startDate));
            }
            if (!endDateField.getText().trim().isEmpty()) {
                LocalDate endDate = LocalDate.parse(endDateField.getText().trim(), dateFormatter);
                filteredTransactions.removeIf(t -> t.getDate().isAfter(endDate));
            }
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter dates in the format MM/dd/yyyy",
                "Invalid Date Format",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Description filter
        String description = descriptionField.getText().trim().toLowerCase();
        if (!description.isEmpty()) {
            filteredTransactions.removeIf(t -> !t.getDescription().toLowerCase().contains(description));
        }

        // Vendor filter
        String vendor = vendorField.getText().trim().toLowerCase();
        if (!vendor.isEmpty()) {
            filteredTransactions.removeIf(t -> !t.getVendor().toLowerCase().contains(vendor));
        }

        // Amount range filter
        try {
            if (!minAmountField.getText().trim().isEmpty()) {
                double minAmount = Double.parseDouble(minAmountField.getText().trim());
                filteredTransactions.removeIf(t -> t.getAmount() < minAmount);
            }
            if (!maxAmountField.getText().trim().isEmpty()) {
                double maxAmount = Double.parseDouble(maxAmountField.getText().trim());
                filteredTransactions.removeIf(t -> t.getAmount() > maxAmount);
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter valid numbers for amount range",
                "Invalid Amount",
                JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Update table
        tableModel.setRowCount(0);
        for (Transaction transaction : filteredTransactions) {
            tableModel.addRow(new Object[]{
                transaction.getDate().format(dateFormatter),
                transaction.getTime().format(timeFormatter),
                transaction.getDescription(),
                transaction.getVendor(),
                transaction.getAmount()
            });
        }

        // Show result count
        JOptionPane.showMessageDialog(this,
            String.format("Found %d matching transactions", filteredTransactions.size()),
            "Search Results",
            JOptionPane.INFORMATION_MESSAGE);
    }
}