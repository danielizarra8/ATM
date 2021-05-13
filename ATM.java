import java.util.Scanner;

public class ATM {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        // int bank instance.
        Bank bank = new Bank("Bank of Ireland");

        //create an user of the bank with the addUser method which creates and account too.
        User user = bank.addUSer("John", "Doe", "1234");

        Account newAccount = new Account("Checking",user,bank);
        user.addAccount(newAccount);
        bank.addAcount(newAccount);

        User curUser;
        // the scanner sc is passed to the worker methods.
        while (true){
            //Stay in the logg in prompt until successful login.
            curUser = ATM.mainMenuPrompt(bank, sc);
            // loop in the menu until the user finishes.
            ATM.printUserMenu(curUser, sc);
        }

    }

    public static void printUserMenu(User user, Scanner sc) {
        //print a summary of the user's account.
        user.printAccountSummary();
        // init
        int choice;

        //user menu

        do{
            System.out.printf("Welcome %s, what would you like to do? \n", user.getFirstName());
            System.out.println(" 1) Show account transaction history");
            System.out.println(" 2) Withdraw");
            System.out.println(" 3) Deposit");
            System.out.println(" 4) Transfer");
            System.out.println(" 5) Quit");
            System.out.println("===========================");
            System.out.print("Select an option");
            choice = sc.nextInt();
            // validate user input (numbers option only 1 to 5).
            if (choice < 1 || choice > 5){
                System.out.println("Invalid choice!, please select an option from 1 t 5");
            }
        }while(choice <1 || choice > 5);
        //if the input is valid, that option is processed.
        switch (choice){
            case 1:
                ATM.showTransHistory(user, sc);
                break;
            case 2:
                ATM.withdrawMoney(user,sc);
                break;
            case 3:
                ATM.depositMoney(user,sc);
                break;
            case 4:
                ATM.transferMoney(user,sc);
                break;
            case 5:
                sc.nextLine();
                break;
        }
        // recursively redisplay the menu unless the user selects quit.
        if (choice != 5){
            ATM.printUserMenu(user,sc);
        }
    }

    public static User mainMenuPrompt(Bank bank, Scanner sc) {
        String userID;
        String pin;
        User authUser;
        //prompt the user for the credentials(id/pin) until they are correct.
        do{
            System.out.printf("\n\n Welcome to %s \n\n", bank.getName());
            System.out.println("Enter user ID: ");
            userID = sc.nextLine();
            System.out.println("Enter user PIN: ");
            pin = sc.nextLine();

            // validate the data passed above by the user with the list of users to check if they match
            // and return the user if matched.
            authUser = bank.userLoggin(userID,pin);
            if(authUser == null){
                System.out.println("Incorrect user ID or Pin! Please try again.");
            }
        }while (authUser == null); // continue looping until successful login
        return  authUser;
    }
    // show the transaction history of the account.
    public static void showTransHistory(User user, Scanner sc) {
        int theAccount;
        // ask the user which account to show history.
        do{
            System.out.printf("Enter the number (1-%d) of the account you want to see: ", user.numAccounts());
            // -1 as the indexing starts at 0.
            theAccount = sc.nextInt()-1;
            // check the input of the user is a valid account.
            if(theAccount < 0 || theAccount > user.numAccounts()){
                System.out.println("Invalid account! Please try again.");
            }
        }while (theAccount < 0 || theAccount > user.numAccounts());
        user.printAccTransHistory(theAccount);
    }
    // process the transferring funds from one account to another.
    public static void transferMoney(User user, Scanner sc) {
        //init fromAcc, toAcc to capture from where and to what account the funds are going.
        int fromAcc;
        int toAcc;
        double amount;
        double accBal;
        // get the account to transfer from.
        do{
            System.out.printf("Enter the number (1-%d) of the account to transfer from: ", user.numAccounts());
            fromAcc = sc.nextInt()-1;
            if(fromAcc < 0 || fromAcc >= user.numAccounts()){
                System.out.println("Invalid account number! Please try again.");
            }
        }while (fromAcc < 0 || fromAcc >= user.numAccounts());
        accBal = user.getAccBalance(fromAcc);

        //get the account to transfer to.
        do{
            System.out.printf("Enter the number (1-%d) of the account to transfer to: ", user.numAccounts());
            toAcc = sc.nextInt()-1;
            if(toAcc < 0 || toAcc >= user.numAccounts()){
                System.out.println("Invalid account number! Please try again.");
            }
        }while (toAcc < 0 || toAcc >= user.numAccounts());
        // get the amount to transfer.
        do {
            System.out.printf("Enter the amount to transfer MAX(€%.02f):", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Invalid amount, you have selected a negative number!");
            }else if (amount > accBal){
                System.out.println("Invalid amount, you do not have sufficient funds! \n your balance is: " + accBal);
            }
        }while (amount < 0 || amount > accBal);
        // after validating the funds, the transfer can be processed.
        // first we substract the amount from the account.
        user.addAccTransaction(fromAcc, -1*amount, String.format("Transfer to account %s", user.getAccUUID(toAcc)));
        // then the amount is added to the account is going to.
        user.addAccTransaction(toAcc, amount, String.format("Transfer to account %s", user.getAccUUID(fromAcc)));
    }
    // process the fund withdrawal from a particular account.
    public static void withdrawMoney(User user, Scanner sc){
        //init fromAcc, toAcc to capture from where and to what account the funds are going.
        int fromAcc;
        double amount;
        double accBal;
        String memo;
        // get the account to transfer from.
        do{
            System.out.printf("Enter the number (1-%d) of the account to withdraw from: ", user.numAccounts());
            fromAcc = sc.nextInt()-1;
            if(fromAcc < 0 || fromAcc >= user.numAccounts()){
                System.out.println("Invalid account number! Please try again.");
            }
        }while (fromAcc < 0 || fromAcc >= user.numAccounts());
            accBal = user.getAccBalance(fromAcc);
        // get the amount to transfer.
        do {
            System.out.printf("Enter the amount you want to withdraw MAX(€%.02f):", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Invalid amount, you have selected a negative number!");
            }else if (amount > accBal){
                System.out.println("Invalid amount, you do not have sufficient funds! \n your balance is: " + accBal);
            }
        }while (amount < 0 || amount > accBal);

        //gobble up rest of previous input.
        sc.nextLine();
        //get the memo.
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        //do the withdrawal.
        user.addAccTransaction(fromAcc, -1*amount, memo);
    }
    // process a fund deposit to a given acount.
    public static void depositMoney(User user, Scanner sc) {
        int toAcc;
        double amount;
        double accBal;
        String memo;
        // get the account to transfer from.
        do{
            System.out.printf("Enter the number (1-%d) of the account to deposit in: ", user.numAccounts());
            toAcc = sc.nextInt()-1;
            if(toAcc < 0 || toAcc >= user.numAccounts()){
                System.out.println("Invalid account number! Please try again.");
            }
        }while (toAcc < 0 || toAcc >= user.numAccounts());
        accBal = user.getAccBalance(toAcc);
        // get the amount to transfer.
        do {
            System.out.printf("Enter the amount to deposit. Current balance: €%.02f:", accBal);
            amount = sc.nextDouble();
            if(amount < 0){
                System.out.println("Invalid amount, you have selected a negative number!");
            }
        }while (amount < 0);

        //gobble up rest of previous input.
        sc.nextLine();
        //get the memo.
        System.out.println("Enter a memo: ");
        memo = sc.nextLine();
        //do the withdrawal.
        user.addAccTransaction(toAcc, amount, memo);
    }
}
