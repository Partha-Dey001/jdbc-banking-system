package com.telusko.learning;

import java.sql.*;

public class BankDAO {

    private static final String URL = ConfigLoader.get("DB_URL");
    private static final String USER = ConfigLoader.get("DB_USER");
    private static final String PASSWORD = ConfigLoader.get("DB_PASS");

    Connection con = null;

    public void connect(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e);
            throw new RuntimeException(e);
        }
    }

    public void createAccount(Account account){
        String query = "INSERT INTO accounts (name, balance) VALUES (? , ?)";

        try{
            connect();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, account.getName());
            ps.setDouble(2, account.getBalance());
            ps.executeUpdate();
            System.out.println("Account created successfully...");
            ps.close();
            con.close();
        } catch (SQLException e) {
            System.out.println("Connection error, account not created");
            System.out.println(e);
            //throw new RuntimeException(e);
        }
    }

    public void checkBalance(int accountId) {
        String query = "SELECT balance FROM accounts WHERE id = ?";

        try {
            connect();
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, accountId);
            ResultSet rs = ps.executeQuery();
            if(rs.next()){
                System.out.println("Balance: " + rs.getDouble("balance"));
            } else {
                System.out.println("Account not found");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}