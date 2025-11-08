package gui;

import classes.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MainGUI extends JFrame {
    private CardLayout cardLayout;
    private JPanel mainPanel;
    public static final String HOME_PANEL = "Home";
    public static final String LEDGER_PANEL = "Ledger";
    public static final String REPORTS_PANEL = "Reports";
    public static final String BALANCE_PANEL = "Balance";
    public static final String ADD_SALE_PANEL = "Add Sale";
    public static final String ADD_PURCHASE_PANEL = "Add Purchase";
    public static final String CUSTOM_SEARCH_PANEL = "Custom Search";

    public MainGUI() {
        // Initialize TransactionHandler
        TransactionHandler.LoadTransactions();
        
        // Set up the main frame
        setTitle("LE-FLIP — Buy • Sell • Profit");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 700);
        setLocationRelativeTo(null);

        // Create menu bar
        setupMenuBar();

        // Set up main panel with card layout
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        // Create and add all panels
        mainPanel.add(new HomePanel(this), HOME_PANEL);
        mainPanel.add(new LedgerPanel(this), LEDGER_PANEL);
        mainPanel.add(new ReportsPanel(this), REPORTS_PANEL);
        mainPanel.add(new BalancePanel(this), BALANCE_PANEL);
        mainPanel.add(new TransactionPanel(this, true), ADD_SALE_PANEL);
        mainPanel.add(new TransactionPanel(this, false), ADD_PURCHASE_PANEL);
        mainPanel.add(new CustomSearchPanel(this), CUSTOM_SEARCH_PANEL);

        add(mainPanel);

        // Show home panel by default
        cardLayout.show(mainPanel, HOME_PANEL);
    }

    private void setupMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        // File menu
        JMenu fileMenu = new JMenu("File");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        fileMenu.add(exitItem);

        // Navigation menu
        JMenu navMenu = new JMenu("Navigation");
        addNavMenuItem(navMenu, "Home", HOME_PANEL);
        navMenu.addSeparator();
        addNavMenuItem(navMenu, "Add Sale", ADD_SALE_PANEL);
        addNavMenuItem(navMenu, "Add Purchase", ADD_PURCHASE_PANEL);
        navMenu.addSeparator();
        addNavMenuItem(navMenu, "Ledger", LEDGER_PANEL);
        addNavMenuItem(navMenu, "Reports", REPORTS_PANEL);
        addNavMenuItem(navMenu, "Balance Summary", BALANCE_PANEL);
        addNavMenuItem(navMenu, "Custom Search", CUSTOM_SEARCH_PANEL);

        // Help menu
        JMenu helpMenu = new JMenu("Help");
        JMenuItem aboutItem = new JMenuItem("About");
        aboutItem.addActionListener(e -> showAboutDialog());
        helpMenu.add(aboutItem);

        menuBar.add(fileMenu);
        menuBar.add(navMenu);
        menuBar.add(helpMenu);
        setJMenuBar(menuBar);
    }

    private void addNavMenuItem(JMenu menu, String text, String cardName) {
        JMenuItem item = new JMenuItem(text);
        item.addActionListener(e -> cardLayout.show(mainPanel, cardName));
        menu.add(item);
    }

    private void showAboutDialog() {
        JOptionPane.showMessageDialog(this,
            "LE-FLIP v1.0\n" +
            "Your personal reseller assistant\n\n" +
            "Track sales, purchases, and profits easily",
            "About LE-FLIP",
            JOptionPane.INFORMATION_MESSAGE);
    }

    public void switchToPanel(String panelName) {
        System.out.println("Switching to panel: " + panelName);
        cardLayout.show(mainPanel, panelName);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    public static void main(String[] args) {
        try {
            // Set system look and feel
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception e) {
            e.printStackTrace();
        }

        SwingUtilities.invokeLater(() -> {
            MainGUI gui = new MainGUI();
            gui.setVisible(true);
        });
    }
}