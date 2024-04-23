import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

class BankAccount {
    private static int nextAccountNumber = 1000;
    private int accountNumber;
    private String name;
    private double balance; // balance in rupees
    private String pin;

    public BankAccount(String name, String pin, double balance) {
        this.accountNumber = ++nextAccountNumber;
        this.name = name;
        this.pin = pin;
        this.balance = balance;
    }

    public int getAccountNumber() {
        return accountNumber;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }

    public String getPin() {
        return pin;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount to withdraw.");
            return;
        }
        if (amount > balance) {
            System.out.println("Insufficient balance.");
            return;
        }
        balance -= amount;
        System.out.println("Withdrawn: ₹" + amount);
    }

    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount to deposit.");
            return;
        }
        balance += amount;
        System.out.println("Deposited: ₹" + amount);
    }

    public void checkBalance() {
        System.out.println("Current Balance: ₹" + balance);
    }
}

public class ATMInterface extends JFrame implements ActionListener {
    private JTextField display;
    private BankAccount account;

    public ATMInterface(BankAccount account) {
        this.account = account;

        setTitle("ATM Interface");
        setSize(300, 250);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center the window

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 1));

        display = new JTextField();
        display.setEditable(false);
        panel.add(display);

        JButton withdrawButton = new JButton("Withdraw");
        withdrawButton.addActionListener(this);
        panel.add(withdrawButton);

        JButton depositButton = new JButton("Deposit");
        depositButton.addActionListener(this);
        panel.add(depositButton);

        JButton balanceButton = new JButton("Check Balance");
        balanceButton.addActionListener(this);
        panel.add(balanceButton);

        add(panel);
        setVisible(true);
    }

    public static void main(String[] args) {
        String name = "Spoorthy"; // Default name for demonstration
        String pin = "1234"; // Default pin for demonstration
        double balance = 1000.0; // Default balance for demonstration
        BankAccount userAccount = new BankAccount(name, pin, balance);
        new ATMInterface(userAccount);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        String command = e.getActionCommand();

        // Ask for PIN before processing the transaction
        String inputPin = JOptionPane.showInputDialog("Enter your 4-digit PIN:");
        if (!inputPin.equals(account.getPin())) {
            JOptionPane.showMessageDialog(this, "Invalid PIN. Transaction canceled.");
            return;
        }

        switch (command) {
            case "Withdraw":
                performWithdraw();
                break;
            case "Deposit":
                performDeposit();
                break;
            case "Check Balance":
                checkBalance();
                break;
            default:
                break;
        }
    }

    private void performWithdraw() {
        String amountStr = JOptionPane.showInputDialog("Enter amount to withdraw (in Rupees):");
        try {
            double amount = Double.parseDouble(amountStr);
            account.withdraw(amount);
            display.setText("Name: " + account.getName() + "\tAccount Number: " + account.getAccountNumber() + "\tNew Balance: ₹" + account.getBalance()+"\t");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void performDeposit() {
        String amountStr = JOptionPane.showInputDialog("Enter amount to deposit (in Rupees):");
        try {
            double amount = Double.parseDouble(amountStr);
            account.deposit(amount);
            display.setText("Name: " + account.getName() + "\tAccount Number: " + account.getAccountNumber() + "\tNew Balance: ₹" + account.getBalance()+"\t");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Invalid input!");
        }
    }

    private void checkBalance() {
        account.checkBalance();
        display.setText("Name: " + account.getName() + "\tAccount Number: " + account.getAccountNumber() + "\tCurrent Balance: ₹" + account.getBalance()+"\t");
    }
}
