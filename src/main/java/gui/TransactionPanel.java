package gui;

import classes.Transaction;
import classes.TransactionHandler;
import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class TransactionPanel extends JPanel {
    private MainGUI mainGUI;
    private boolean isSale;
    private JTextField amountField;
    private JTextField descriptionField;
    private JTextField vendorField;
    private JLabel messageLabel;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("MM/dd/yyyy");
    private static final DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");

    public TransactionPanel(MainGUI mainGUI, boolean isSale) {
        this.mainGUI = mainGUI;
        this.isSale = isSale;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel(isSale ? "Add Sale" : "Add Purchase");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Form panel
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // Amount field
        gbc.gridx = 0; gbc.gridy = 0;
        formPanel.add(new JLabel("Amount ($):"), gbc);
        gbc.gridx = 1;
        amountField = new JTextField(20);
        formPanel.add(amountField, gbc);

        // Description field
        gbc.gridx = 0; gbc.gridy = 1;
        formPanel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1;
        descriptionField = new JTextField(20);
        formPanel.add(descriptionField, gbc);

        // Vendor field
        gbc.gridx = 0; gbc.gridy = 2;
        formPanel.add(new JLabel(isSale ? "Sold To:" : "Purchased From:"), gbc);
        gbc.gridx = 1;
        vendorField = new JTextField(20);
        formPanel.add(vendorField, gbc);

        // Message label for feedback
        gbc.gridx = 0; gbc.gridy = 3; gbc.gridwidth = 2;
        messageLabel = new JLabel(" ");
        messageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        formPanel.add(messageLabel, gbc);

        // Buttons panel
        gbc.gridx = 0; gbc.gridy = 4;
        JPanel buttonsPanel = new JPanel();
        JButton saveButton = new JButton("Save");
        JButton cancelButton = new JButton("Cancel");

        saveButton.addActionListener(e -> saveTransaction());
        cancelButton.addActionListener(e -> {
            clearFields();
            mainGUI.switchToPanel("Home");
        });

        buttonsPanel.add(saveButton);
        buttonsPanel.add(cancelButton);
        formPanel.add(buttonsPanel, gbc);

        // Add all panels
        add(titlePanel, BorderLayout.NORTH);
        add(formPanel, BorderLayout.CENTER);
    }

    private void saveTransaction() {
        try {
            double amount = Double.parseDouble(amountField.getText().trim());
            String description = descriptionField.getText().trim();
            String vendor = vendorField.getText().trim();

            if (description.isEmpty() || vendor.isEmpty()) {
                showError("Please fill in all fields");
                return;
            }

            // Adjust amount sign based on transaction type
            if (!isSale) {
                amount = -Math.abs(amount);
            }

            // Create and save transaction
            Transaction transaction = new Transaction(
                LocalDate.now(),
                LocalTime.now(),
                description,
                vendor,
                amount
            );

            TransactionHandler.getTransactions().add(transaction);
            // Save to file logic would go here

            // Write to file
            try {
                FileWriter fileWriter = new FileWriter("transactions.csv", true);
                BufferedWriter bufWriter = new BufferedWriter(fileWriter);
                bufWriter.write("\n" + transaction.getDate().format(dateFormatter) + "|" + 
                              transaction.getTime().format(timeFormatter) + "|" + 
                              transaction.getDescription() + "|" + 
                              transaction.getVendor() + "|" + 
                              transaction.getAmount());
                bufWriter.close();
                showSuccess("Transaction saved successfully!");
                clearFields();
            } catch (IOException e) {
                showError("Error saving transaction to file");
                e.printStackTrace();
            }

        } catch (NumberFormatException e) {
            showError("Please enter a valid amount");
        }
    }

    private void showError(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(Color.RED);
    }

    private void showSuccess(String message) {
        messageLabel.setText(message);
        messageLabel.setForeground(new Color(0, 150, 0));
    }

    private void clearFields() {
        amountField.setText("");
        descriptionField.setText("");
        vendorField.setText("");
        messageLabel.setText(" ");
    }
}