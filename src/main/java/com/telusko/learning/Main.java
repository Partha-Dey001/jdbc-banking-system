package com.telusko.learning;

import java.util.Scanner;

public class Main {

    private static final String passWord = "energy";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\n🏦 BANK SYSTEM MENU 🏦");
            System.out.println("1. Create Account");
            System.out.println("2. Check Balance");
            System.out.println("3. Deposit Money");
            System.out.println("4. Withdraw Money");
            System.out.println("5. Show all accounts details in bank");
            System.out.println("6. Fund transfer");
            System.out.println("7. Exit");
            System.out.print("Enter choice: ");

            if (!scanner.hasNext()) {  // ✅ Check if there's input available
                System.out.println("No more input available. Exiting...");
                break;
            }

            if (scanner.hasNextInt()) {  // ✅ Ensure input is an integer
                int choice = scanner.nextInt();
                scanner.nextLine();  // ✅ Consume the leftover newline safely

                switch (choice) {
                    case 1:
                        createAcc();
                        break;

                    case 2:
                        checkBal();
                        break;

                    case 3:
                        depositBal();
                        break;

                    case 4:
                        withdrawBal();
                        break;

                    case 5:
                        showAcc();
                        break;

                    case 6:
                        transferFunds();
                        break;

                    case 7:
                        System.out.println("Thank you for using the Banking System 🙏");
                        scanner.close();
                        return;  // ✅ Use return instead of break to exit cleanly

                    default:
                        System.out.println("Invalid choice, Please try again!");
                        break;
                }
            } else {
                System.out.println("Invalid input! Please enter a number.");
                scanner.next();  // ✅ Consume invalid input to avoid infinite loop
            }
        }

        scanner.close();  // ✅ Close scanner at the end
        System.out.println("Thank you, please visit again.");
    }

    static void createAcc() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("PLease enter customer name:");
        String name = scanner.nextLine();
        System.out.println("PLease enter the amount you want to deposit (Minimum deposit amount: 500) :");
        double amount = scanner.nextDouble();
        Account account = new Account(name, amount);
        BankDAO dao = new BankDAO();
        dao.createAccount(account);
    }

    static void checkBal() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter your account id: ");
        int accountId = sc.nextInt();
        BankDAO dao = new BankDAO();
        dao.checkBalance(accountId);
    }

    static void depositBal() {
        Scanner sc = new Scanner(System.in);
        System.out.print("PLease enter customer id: ");
        int accountId = sc.nextInt();
        System.out.print("PLease enter the amount you want to deposit (Minimum deposit amount: 500): ");
        double amount = sc.nextDouble();
        BankService service = new BankService();
        service.deposit(accountId, amount);
    }

    static void withdrawBal() {
        Scanner sc = new Scanner(System.in);
        System.out.print("PLease enter customer id: ");
        int accountId = sc.nextInt();
        System.out.print("PLease enter the amount you want to withdraw: ");
        double amount = sc.nextDouble();
        BankService service = new BankService();
        service.withdraw(accountId, amount);
    }

    static void showAcc(){
        BankService bankService = new BankService();
        System.out.print("Enter administrative password: ");
        Scanner sc = new Scanner(System.in);
        String in = sc.nextLine();
        if(in.equals(passWord)) {
            bankService.showAllAccounts();
        } else {
            System.out.println("Please enter valid password!");
        }
    }

    static void transferFunds (){
        Scanner sc = new Scanner(System.in);
        System.out.print("Please enter your account Id: ");
        int sender = sc.nextInt();
        System.out.println("Enter receiver's account Id: ");
        int receiver = sc.nextInt();
        System.out.println("Enter the amount: ");
        double amount = sc.nextDouble();
        System.out.println("Please wait...");
        BankService bankService = new BankService();
        bankService.transfer(sender, receiver, amount);
    }
}