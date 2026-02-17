package atm;

import java.util.ArrayList;

public class User {
    private String name;
    private String pin;
    private double balance;
    private ArrayList<String> transactions;

    public User(String name, String pin, double balance) {
        this.name = name;
        this.pin = pin;
        this.balance = balance;
        this.transactions = new ArrayList<>();
    }

    public String getName() { return name; }
    public String getPin() { return pin; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public ArrayList<String> getTransactions() { return transactions; }
}
