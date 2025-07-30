package com.telusko.learning;

public class Account {
    private int id;
    private String name;
    private double balance;

    Account(String name, double balance) {
        this.name = name;
        this.balance = balance;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public double getBalance() {
        return balance;
    }
}
