package gui;

import javax.swing.*;
import java.awt.*;

public class HomePanel extends JPanel {
    private MainGUI mainGUI;

    public HomePanel(MainGUI mainGUI) {
        this.mainGUI = mainGUI;
        setupUI();
    }

    private void setupUI() {
        setLayout(new BorderLayout());

        // Welcome panel at the top
        JPanel welcomePanel = new JPanel();
        welcomePanel.setLayout(new BoxLayout(welcomePanel, BoxLayout.Y_AXIS));
        welcomePanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel titleLabel = new JLabel("LE-FLIP — Buy • Sell • Profit");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Your personal reseller assistant");
        subtitleLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        subtitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        welcomePanel.add(titleLabel);
        welcomePanel.add(Box.createRigidArea(new Dimension(0, 10)));
        welcomePanel.add(subtitleLabel);

        // Main buttons panel
        JPanel buttonsPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(10, 10, 10, 10);

        // Create main action buttons
        JButton addSaleBtn = createHomeButton("Add Sale", "Record a new sale", MainGUI.ADD_SALE_PANEL);
        JButton addPurchaseBtn = createHomeButton("Add Purchase", "Record a new purchase", MainGUI.ADD_PURCHASE_PANEL);
        JButton ledgerBtn = createHomeButton("View Ledger", "View all transactions", MainGUI.LEDGER_PANEL);
        JButton reportsBtn = createHomeButton("Reports", "View reports and analytics", MainGUI.REPORTS_PANEL);
        JButton balanceBtn = createHomeButton("Balance Summary", "View financial summary", MainGUI.BALANCE_PANEL);
        JButton searchBtn = createHomeButton("Custom Search", "Search transactions", MainGUI.CUSTOM_SEARCH_PANEL);

        // Add buttons to panel in a 2x3 grid
        gbc.gridx = 0; gbc.gridy = 0;
        buttonsPanel.add(addSaleBtn, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(addPurchaseBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 1;
        buttonsPanel.add(ledgerBtn, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(reportsBtn, gbc);
        gbc.gridx = 0; gbc.gridy = 2;
        buttonsPanel.add(balanceBtn, gbc);
        gbc.gridx = 1;
        buttonsPanel.add(searchBtn, gbc);

        // Add panels to main panel
        add(welcomePanel, BorderLayout.NORTH);
        add(buttonsPanel, BorderLayout.CENTER);
    }

    private JButton createHomeButton(String text, String tooltip, String targetPanel) {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setToolTipText(tooltip);
        button.addActionListener(e -> mainGUI.switchToPanel(targetPanel));
        return button;
    }
}