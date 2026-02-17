package atm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class ATM_Machine {

    private ArrayList<User> users = new ArrayList<>();
    private User currentUser = null;
    private JFrame frame;

    public ATM_Machine() {
        showStartScreen();
    }

    // =================== START SCREEN ===================
    public void showStartScreen() {
        frame = new JFrame("ATM Machine");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        JLabel title = new JLabel("Welcome to ATM", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        mainPanel.add(title, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(30, 30, 30));

        JButton registerBtn = createButton("Register");
        JButton loginBtn = createButton("Login");

        buttonPanel.add(registerBtn);
        buttonPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        buttonPanel.add(loginBtn);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(350, 300));
        frame.setVisible(true);

        registerBtn.addActionListener(e -> {
            frame.dispose();
            showRegisterScreen();
        });

        loginBtn.addActionListener(e -> {
            frame.dispose();
            showLoginScreen();
        });
    }

    // =================== REGISTRATION SCREEN ===================
    public void showRegisterScreen() {
        frame = new JFrame("ATM Registration");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        JLabel nameLabel = createLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel pinLabel = createLabel("PIN (4 digits):");
        JPasswordField pinField = new JPasswordField();

        JLabel balanceLabel = createLabel("Initial Deposit:");
        JTextField balanceField = new JTextField();

        JButton registerBtn = createButton("Register");
        JButton backBtn = createButton("Back");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(balanceLabel);
        panel.add(balanceField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(registerBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 300));
        frame.setVisible(true);

        registerBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();
            String balanceText = balanceField.getText().trim();

            if (name.isEmpty() || pin.isEmpty() || balanceText.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            if (pin.length() != 4 || !pin.matches("\\d{4}")) {
                JOptionPane.showMessageDialog(frame, "PIN must be 4 digits!");
                return;
            }

            double balance;
            try {
                balance = Double.parseDouble(balanceText);
                if (balance < 0) {
                    JOptionPane.showMessageDialog(frame, "Balance cannot be negative!");
                    return;
                }
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(frame, "Enter a valid number for balance!");
                return;
            }

            User newUser = new User(name, pin, balance);
            users.add(newUser);

            JOptionPane.showMessageDialog(frame, "Registration successful!");
            frame.dispose();
            showStartScreen();
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            showStartScreen();
        });
    }

    // =================== LOGIN SCREEN ===================
    public void showLoginScreen() {
        frame = new JFrame("ATM Login");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel panel = new JPanel();
        panel.setLayout(new GridLayout(4, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        panel.setBackground(new Color(30, 30, 30));

        JLabel nameLabel = createLabel("Name:");
        JTextField nameField = new JTextField();

        JLabel pinLabel = createLabel("PIN:");
        JPasswordField pinField = new JPasswordField();

        JButton loginBtn = createButton("Login");
        JButton backBtn = createButton("Back");

        panel.add(nameLabel);
        panel.add(nameField);
        panel.add(pinLabel);
        panel.add(pinField);
        panel.add(new JLabel());
        panel.add(new JLabel());
        panel.add(loginBtn);
        panel.add(backBtn);

        frame.add(panel);
        frame.pack();
        frame.setMinimumSize(new Dimension(350, 250));
        frame.setVisible(true);

        loginBtn.addActionListener(e -> {
            String name = nameField.getText().trim();
            String pin = new String(pinField.getPassword()).trim();

            if (name.isEmpty() || pin.isEmpty()) {
                JOptionPane.showMessageDialog(frame, "Please fill all fields!");
                return;
            }

            currentUser = null;
            for (User u : users) {
                if (u.getName().equals(name) && u.getPin().equals(pin)) {
                    currentUser = u;
                    break;
                }
            }

            if (currentUser != null) {
                JOptionPane.showMessageDialog(frame, "Login successful!");
                frame.dispose();
                showATMMenu();
            } else {
                JOptionPane.showMessageDialog(frame, "Invalid credentials!");
            }
        });

        backBtn.addActionListener(e -> {
            frame.dispose();
            showStartScreen();
        });
    }

    // =================== ATM MENU ===================
    public void showATMMenu() {
        frame = new JFrame("ATM - " + currentUser.getName());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBackground(new Color(30, 30, 30));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel header = new JLabel("Welcome, " + currentUser.getName(), SwingConstants.CENTER);
        header.setFont(new Font("Arial", Font.BOLD, 22));
        header.setForeground(Color.WHITE);
        mainPanel.add(header, BorderLayout.NORTH);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBackground(new Color(30, 30, 30));

        String[] buttons = {"Check Balance", "Deposit", "Withdraw", "Transaction History", "Logout"};
        for (String text : buttons) {
            JButton btn = createButton(text);
            btn.setAlignmentX(Component.CENTER_ALIGNMENT);
            btn.setMaximumSize(new Dimension(Integer.MAX_VALUE, 40));
            buttonPanel.add(btn);
            buttonPanel.add(Box.createRigidArea(new Dimension(0, 15)));

            btn.addActionListener(e -> handleATMAction(text));
        }

        mainPanel.add(buttonPanel, BorderLayout.CENTER);

        JLabel footer = new JLabel("ATM Machine", SwingConstants.CENTER);
        footer.setForeground(Color.LIGHT_GRAY);
        mainPanel.add(footer, BorderLayout.SOUTH);

        frame.add(mainPanel);
        frame.pack();
        frame.setMinimumSize(new Dimension(400, 400));
        frame.setVisible(true);
    }

    // =================== BUTTON CREATION HELPER ===================
    private JButton createButton(String text) {
        JButton btn = new JButton(text);
        btn.setBackground(new Color(70, 130, 180));
        btn.setForeground(Color.WHITE);
        btn.setFocusPainted(false);
        btn.setFont(new Font("Arial", Font.BOLD, 16));
        btn.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        return btn;
    }

    private JLabel createLabel(String text) {
        JLabel lbl = new JLabel(text);
        lbl.setForeground(Color.WHITE);
        lbl.setFont(new Font("Arial", Font.PLAIN, 16));
        return lbl;
    }

    // =================== HANDLE ATM BUTTON ACTIONS ===================
    private void handleATMAction(String action) {
        switch (action) {
            case "Check Balance":
                JOptionPane.showMessageDialog(frame, "Your balance is: ₹" + currentUser.getBalance());
                break;
            case "Deposit":
                String depText = JOptionPane.showInputDialog(frame, "Enter deposit amount:");
                if (depText != null) {
                    try {
                        double amount = Double.parseDouble(depText);
                        if (amount > 0) {
                            currentUser.setBalance(currentUser.getBalance() + amount);
                            currentUser.getTransactions().add("Deposited: ₹" + amount);
                            JOptionPane.showMessageDialog(frame, "Deposit successful!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Enter a positive amount!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Enter a valid number!");
                    }
                }
                break;
            case "Withdraw":
                String witText = JOptionPane.showInputDialog(frame, "Enter withdrawal amount:");
                if (witText != null) {
                    try {
                        double amount = Double.parseDouble(witText);
                        if (amount > 0 && amount <= currentUser.getBalance()) {
                            currentUser.setBalance(currentUser.getBalance() - amount);
                            currentUser.getTransactions().add("Withdrew: ₹" + amount);
                            JOptionPane.showMessageDialog(frame, "Withdrawal successful!");
                        } else {
                            JOptionPane.showMessageDialog(frame, "Insufficient balance or invalid amount!");
                        }
                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(frame, "Enter a valid number!");
                    }
                }
                break;
            case "Transaction History":
                ArrayList<String> transactions = currentUser.getTransactions();
                if (transactions.isEmpty()) {
                    JOptionPane.showMessageDialog(frame, "No transactions yet.");
                } else {
                    JTextArea textArea = new JTextArea();
                    for (String t : transactions) {
                        textArea.append(t + "\n");
                    }
                    textArea.setEditable(false);
                    JScrollPane scrollPane = new JScrollPane(textArea);
                    scrollPane.setPreferredSize(new Dimension(300, 200));
                    JOptionPane.showMessageDialog(frame, scrollPane, "Transaction History", JOptionPane.INFORMATION_MESSAGE);
                }
                break;
            case "Logout":
                currentUser = null;
                frame.dispose();
                showStartScreen();
                break;
        }
    }

    public static void main(String[] args) {
        new ATM_Machine();
    }
}
