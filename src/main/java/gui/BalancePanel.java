package gui;

import classes.Transaction;
import classes.TransactionHandler;
import javax.swing.*;
import java.awt.*;
import java.time.LocalDate;

public class BalancePanel extends JPanel {
    private MainGUI mainGUI;
    private JLabel balanceLabel;
    private JPanel summaryPanel;

    public BalancePanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Title panel
        JPanel titlePanel = new JPanel();
        JLabel titleLabel = new JLabel("Balance Summary");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titlePanel.add(titleLabel);

        // Current balance panel
        JPanel balancePanel = new JPanel();
        balancePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        balanceLabel = new JLabel();
        balanceLabel.setFont(new Font("Arial", Font.BOLD, 24));
        balancePanel.add(balanceLabel);

        // Summary panels
        summaryPanel = new JPanel();
        summaryPanel.setLayout(new BoxLayout(summaryPanel, BoxLayout.Y_AXIS));
        
        // Add components
        add(titlePanel, BorderLayout.NORTH);
        add(balancePanel, BorderLayout.CENTER);
        add(summaryPanel, BorderLayout.SOUTH);

        // Refresh button
        JButton refreshButton = new JButton("Refresh");
        refreshButton.addActionListener(e -> updateBalance());
        add(refreshButton, BorderLayout.SOUTH);

        // Initial update
        updateBalance();
    }

    private void updateBalance() {
        double currentBalance = 0;
        double monthlyIncome = 0;
        double monthlyExpenses = 0;
        double yearlyIncome = 0;
        double yearlyExpenses = 0;

        LocalDate today = LocalDate.now();
        LocalDate startOfMonth = today.withDayOfMonth(1);
        LocalDate startOfYear = today.withDayOfYear(1);

        for (Transaction transaction : TransactionHandler.getTransactions()) {
            double amount = transaction.getAmount();
            LocalDate date = transaction.getDate();

            // Update current balance
            currentBalance += amount;

            // Monthly calculations
            if (!date.isBefore(startOfMonth) && !date.isAfter(today)) {
                if (amount > 0) {
                    monthlyIncome += amount;
                } else {
                    monthlyExpenses += Math.abs(amount);
                }
            }

            // Yearly calculations
            if (!date.isBefore(startOfYear) && !date.isAfter(today)) {
                if (amount > 0) {
                    yearlyIncome += amount;
                } else {
                    yearlyExpenses += Math.abs(amount);
                }
            }
        }

        // Update balance display
        balanceLabel.setText(String.format("Current Balance: $%.2f", currentBalance));
        balanceLabel.setForeground(currentBalance >= 0 ? new Color(0, 150, 0) : Color.RED);

        // Clear and update summary panel
        summaryPanel.removeAll();
        summaryPanel.add(createSummarySection("This Month", monthlyIncome, monthlyExpenses));
        summaryPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        summaryPanel.add(createSummarySection("This Year", yearlyIncome, yearlyExpenses));
        
        // Refresh the panel
        revalidate();
        repaint();
    }

    private JPanel createSummarySection(String title, double income, double expenses) {
        JPanel panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createTitledBorder(title));

        double netProfit = income - expenses;

        panel.add(createSummaryLabel("Income:", income, true));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(createSummaryLabel("Expenses:", expenses, false));
        panel.add(Box.createRigidArea(new Dimension(0, 5)));
        panel.add(createSummaryLabel("Net Profit:", netProfit, netProfit >= 0));

        return panel;
    }

    private JLabel createSummaryLabel(String label, double amount, boolean isPositive) {
        JLabel summaryLabel = new JLabel(String.format("%s $%.2f", label, Math.abs(amount)));
        summaryLabel.setForeground(isPositive ? new Color(0, 150, 0) : Color.RED);
        summaryLabel.setAlignmentX(Component.LEFT_ALIGNMENT);
        return summaryLabel;
    }
}