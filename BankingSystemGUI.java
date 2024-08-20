// src/BankingSystemGUI.java
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BankingSystemGUI extends JFrame {
    private Bank bank;
    private JTextField accountNumberField;
    private JTextField accountHolderField;
    private JTextArea displayArea;

    public BankingSystemGUI() {
        bank = new Bank();
        setTitle("Simple Banking System");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel for account creation
        JPanel createAccountPanel = new JPanel();
        createAccountPanel.setLayout(new GridLayout(3, 2));

        createAccountPanel.add(new JLabel("Account Number:"));
        accountNumberField = new JTextField();
        createAccountPanel.add(accountNumberField);

        createAccountPanel.add(new JLabel("Account Holder Name:"));
        accountHolderField = new JTextField();
        createAccountPanel.add(accountHolderField);

        JButton createAccountButton = new JButton("Create Account");
        createAccountButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createAccount();
            }
        });
        createAccountPanel.add(createAccountButton);

        // Panel for displaying information
        JPanel displayPanel = new JPanel();
        displayPanel.setLayout(new BorderLayout());

        displayArea = new JTextArea();
        displayArea.setEditable(false);
        displayPanel.add(new JScrollPane(displayArea), BorderLayout.CENTER);

        // Panel for account operations
        JPanel operationsPanel = new JPanel();
        operationsPanel.setLayout(new GridLayout(3, 2));

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deposit();
            }
        });
        operationsPanel.add(depositButton);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                withdraw();
            }
        });
        operationsPanel.add(withdrawButton);

        JButton checkBalanceButton = new JButton("Check Balance");
        checkBalanceButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                checkBalance();
            }
        });
        operationsPanel.add(checkBalanceButton);

        JButton displayAccountsButton = new JButton("Display All Accounts");
        displayAccountsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                displayAllAccounts();
            }
        });
        operationsPanel.add(displayAccountsButton);

        // Adding panels to the frame
        add(createAccountPanel, BorderLayout.NORTH);
        add(displayPanel, BorderLayout.CENTER);
        add(operationsPanel, BorderLayout.SOUTH);
    }

    private void createAccount() {
        String accountNumber = accountNumberField.getText();
        String accountHolderName = accountHolderField.getText();

        if (!accountNumber.isEmpty() && !accountHolderName.isEmpty()) {
            bank.createAccount(accountNumber, accountHolderName);
            displayArea.append("Account created: " + accountHolderName + "\n");
        } else {
            JOptionPane.showMessageDialog(this, "Please enter account number and holder name.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deposit() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber != null) {
            Account account = bank.getAccount(accountNumber);
            if (account != null) {
                String amountStr = JOptionPane.showInputDialog(this, "Enter deposit amount:");
                if (amountStr != null) {
                    double amount = Double.parseDouble(amountStr);
                    account.deposit(amount);
                    displayArea.append("Deposited: $" + amount + " into account " + accountNumber + "\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void withdraw() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber != null) {
            Account account = bank.getAccount(accountNumber);
            if (account != null) {
                String amountStr = JOptionPane.showInputDialog(this, "Enter withdrawal amount:");
                if (amountStr != null) {
                    double amount = Double.parseDouble(amountStr);
                    account.withdraw(amount);
                    displayArea.append("Withdrawn: $" + amount + " from account " + accountNumber + "\n");
                }
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void checkBalance() {
        String accountNumber = JOptionPane.showInputDialog(this, "Enter account number:");
        if (accountNumber != null) {
            Account account = bank.getAccount(accountNumber);
            if (account != null) {
                displayArea.append("Account Balance (" + account.getAccountHolderName() + "): $" + account.getBalance() + "\n");
            } else {
                JOptionPane.showMessageDialog(this, "Account not found.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void displayAllAccounts() {
        displayArea.append("All Accounts:\n");
        for (Account account : bank.getAllAccounts()) {
            displayArea.append(account.getAccountNumber() + " - " + account.getAccountHolderName() + " - $" + account.getBalance() + "\n");
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new BankingSystemGUI().setVisible(true);
            }
        });
    }
}
