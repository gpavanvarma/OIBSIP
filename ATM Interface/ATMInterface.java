import java.util.Scanner;

class Transaction {
    String details;

    Transaction(String details) {
        this.details = details;
    }
}

class User {
    String userId;
    String userPin;
    double balance;
    Transaction[] transactions;
    int transactionCount;

    User(String userId, String userPin, double balance) {
        this.userId = userId;
        this.userPin = userPin;
        this.balance = balance;
        this.transactions = new Transaction[100];
        this.transactionCount = 0;
    }

    void addTransaction(String details) {
        if (transactionCount < 100) {
            transactions[transactionCount++] = new Transaction(details);
        }
    }

    void showTransactionHistory() {
        if (transactionCount == 0) {
            System.out.println("No transactions yet.");
        } else {
            for (int i = 0; i < transactionCount; i++) {
                System.out.println(transactions[i].details);
            }
        }
    }
}

class ATM {
    Scanner sc = new Scanner(System.in);
    User user;
    User[] users;

    ATM(User[] users, User user) {
        this.users = users;
        this.user = user;
    }

    void showMenu() {
        while (true) {
            System.out.println("\n1. Transactions History");
            System.out.println("2. Withdraw");
            System.out.println("3. Deposit");
            System.out.println("4. Transfer");
            System.out.println("5. Quit");
            System.out.print("Choose an option: ");
            int choice = sc.nextInt();

            switch (choice) {
                case 1 -> user.showTransactionHistory();
                case 2 -> withdraw();
                case 3 -> deposit();
                case 4 -> transfer();
                case 5 -> {
                    System.out.println("Thank you for using the ATM.");
                    return;
                }
                default -> System.out.println("Invalid choice.");
            }
        }
    }

    void withdraw() {
        System.out.print("Enter amount to withdraw: ");
        double amount = sc.nextDouble();
        if (amount > 0 && amount <= user.balance) {
            user.balance -= amount;
            user.addTransaction("Withdrawn: " + amount);
            System.out.println("Withdrawal successful. Current balance: " + user.balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    void deposit() {
        System.out.print("Enter amount to deposit: ");
        double amount = sc.nextDouble();
        if (amount > 0) {
            user.balance += amount;
            user.addTransaction("Deposited: " + amount);
            System.out.println("Deposit successful. Current balance: " + user.balance);
        } else {
            System.out.println("Invalid amount.");
        }
    }

    void transfer() {
        System.out.print("Enter recipient user ID: ");
        String receiverId = sc.next();
        System.out.print("Enter amount to transfer: ");
        double amount = sc.nextDouble();

        User receiver = findUser(receiverId);
        if (receiver == null) {
            System.out.println("Recipient not found.");
            return;
        }

        if (amount > 0 && amount <= user.balance) {
            user.balance -= amount;
            receiver.balance += amount;
            user.addTransaction("Transferred " + amount + " to " + receiver.userId);
            receiver.addTransaction("Received " + amount + " from " + user.userId);
            System.out.println("Transfer successful. Current balance: " + user.balance);
        } else {
            System.out.println("Invalid amount or insufficient balance.");
        }
    }

    User findUser(String id) {
        for (User u : users) {
            if (u != null && u.userId.equals(id)) {
                return u;
            }
        }
        return null;
    }
}

class Bank {
    User[] users;

    Bank() {
        users = new User[5];
        users[0] = new User("user1", "1111", 1000);
        users[1] = new User("user2", "2222", 2000);
    }

    User login(String userId, String userPin) {
        for (User u : users) {
            if (u != null && u.userId.equals(userId) && u.userPin.equals(userPin)) {
                return u;
            }
        }
        return null;
    }
}

public class ATMInterface {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Bank bank = new Bank();
        System.out.print("Enter User ID: ");
        String userId = sc.next();
        System.out.print("Enter User PIN: ");
        String userPin = sc.next();

        User user = bank.login(userId, userPin);
        if (user != null) {
            ATM atm = new ATM(bank.users, user);
            atm.showMenu();
        } else {
            System.out.println("Invalid credentials.");
        }
    }
}

