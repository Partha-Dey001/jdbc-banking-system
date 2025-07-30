package com.telusko.learning;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class BankService {

    BankDAO dao = new BankDAO();

    public void deposit(int accountId, double amount) {
        String query = "UPDATE accounts SET balance = balance + ? WHERE id = ?";

        try{
            dao.connect();
            PreparedStatement ps = dao.con.prepareStatement(query);
            ps.setDouble(1, amount);
            ps.setInt(2,accountId);
            int updated = ps.executeUpdate();
            if(updated > 0) {
                System.out.println("Deposit successful \n Amount deposited: " + amount);
                logTransaction(accountId, "DEPOSIT", amount);
            }
            ps.close();
            dao.con.close();
        }
        catch (SQLException e){
            System.out.println("Deposit failed!");
            System.out.println(e);
        }
    }

    public void withdraw(int accountId, double amount) {
        String checkingQuery = "SELECT balance FROM accounts WHERE id = ?";
        String withdrawQuery = "UPDATE accounts SET balance = balance - ? WHERE id = ?";

        try {
            dao.connect();
            PreparedStatement checkStatement = dao.con.prepareStatement(checkingQuery);
            checkStatement.setInt(1, accountId);
            ResultSet balanceChecker = checkStatement.executeQuery();
            if(balanceChecker.next()){
                double balance = balanceChecker.getDouble("balance");
                if(balance >= amount) {
                    PreparedStatement withdrawStatement = dao.con.prepareStatement(withdrawQuery);
                    withdrawStatement.setDouble(1, amount);
                    withdrawStatement.setInt(2, accountId);
                    withdrawStatement.executeUpdate();
                    System.out.println("Withdraw successful! \nWithdrawn Amount: "+amount);
                    withdrawStatement.close();
                    logTransaction(accountId, "WITHDRAW", amount);
                } else {
                    System.out.println("Transaction failed! \nInsufficient Funds.");
                }
            } else {
                System.out.println("Account not found.");
            }
            balanceChecker.close();
            dao.con.close();

        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public void transfer(int senderId, int recieverId, double amount) {
        String checkingQuery = "SELECT balance FROM accounts WHERE id = ?";
        String transferQuery = "UPDATE accounts SET balance = balance + ? WHERE id = ?";
        String deduction = "UPDATE accounts SET balance = balance - ? WHERE id = ?";

        try {
            dao.connect();
            PreparedStatement checkStatement = dao.con.prepareStatement(checkingQuery);
            checkStatement.setInt(1,senderId);
            ResultSet rs = checkStatement.executeQuery();
            if(rs.next()){
                double balance = rs.getDouble("balance");
                if(balance >= amount) {
                    PreparedStatement statement = dao.con.prepareStatement(transferQuery);
                    statement.setDouble(1,amount);
                    statement.setInt(2, recieverId);
                    statement.executeUpdate();
                    PreparedStatement statement2 = dao.con.prepareStatement(deduction);
                    statement2.setDouble(1,amount);
                    statement2.setInt(2, senderId);
                    statement2.executeUpdate();
                    statement2.close();
                    statement.close();
                    dao.con.close();
                    System.out.println(amount+" Successfully transferred!");
                    logTransaction(senderId, "TRANSFER", amount);
                } else {
                    System.out.println("Transaction failed!\nInsufficient funds.");
                }
            } else {
                System.out.println("Account not found!");
            }
            checkStatement.close();
            dao.con.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }


    private void logTransaction(int accountId, String type, double amount) {
        String query = "INSERT INTO transactions (account_id, type, amount) VALUES (?, ?, ?)";

        try{
            dao.connect();
            PreparedStatement ps = dao.con.prepareStatement(query);
            ps.setInt(1, accountId);
            ps.setString(2, type);
            ps.setDouble(3, amount);
            ps.executeUpdate();
            ps.close();
            dao.con.close();
        } catch (SQLException e){
            System.out.println(e);
        }
    }

    public void showAllAccounts () {
        String query = "SELECT * FROM accounts";

        try{
            dao.connect();
            Statement st = dao.con.createStatement();
            ResultSet rs = st.executeQuery(query);
            while(rs.next()) {
                System.out.println("Account Holder's name : " + rs.getString("name") +
                        " | Account number : " + rs.getInt("id") +
                        " | Balance : " + rs.getDouble("balance"));
            }
            st.close();
            dao.con.close();
        } catch (SQLException e) {
            System.out.println(e);
        }
    }
}
