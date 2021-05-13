import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.security.MessageDigest;

public class User {
    private String firstName;
    private String lastName;
    private String uuid;
    private byte pinHash[]; //hash md5 to encrypt the pin
    private ArrayList<Account> accounts; // the list of accounts linked to the user.

    public User(String fName, String lName, String pin, Bank theBank){
        //set fields in the constructor
        this.firstName = fName;
        this.lastName = lName;
        // encrypt the pin using the MD5 protocol to hash the password.
        // wrapped up in a try catch for any exceptions thrown by the MesseageDigest.
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            this.pinHash = md.digest(pin.getBytes());
        }catch (NoSuchAlgorithmException e){
            System.out.println("System error: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
        // generate a unique identifer for the user
        this.uuid = theBank.getNewUserUUID();

        // create an empty list of accounts
        this.accounts = new ArrayList<Account>();

        //print log message.

        System.out.printf("New user %s, %s with ID %s created!", lName, fName, this.uuid);

    }

    // add account list coming from the Account class, encapsulated to the addAccount method.
    public void addAccount(Account account) {
        this.accounts.add(account);
    }
    // return the uuid of the user.
    public String getUUID() {
        return this.uuid;
    }

    public boolean validatePin(String pin) {
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            // checking the pin with the hashed pin stored for that particular user.
            return MessageDigest.isEqual(md.digest(pin.getBytes()), this.pinHash);
        }catch (NoSuchAlgorithmException e){
            System.out.println("Pin Error: " + e.toString());
            e.printStackTrace();
            System.exit(1);
        }
        return false;
    }

    public String getFirstName() {
        return this.firstName;
    }
    // to print all the accounts summary
    public void printAccountSummary(){
        System.out.printf("\n\n%s's account summary \n", this.firstName);
        for(int a=0; a < this.accounts.size(); a++){
            System.out.printf("  %d) %s\n", a+1, this.accounts.get(a).getSummaryLine());
        }
        System.out.println();
    }
    // returns the size in number of many accounts for each user.
    public int numAccounts() {
        return this.accounts.size();
    }
    // print transaction history for a particular account.
    // accountIndex is the index of the account to use.
    public void printAccTransHistory(int accountIndex) {
        this.accounts.get(accountIndex).printTransHistory();

    }

    public double getAccBalance(int accountIndex) {
        return this.accounts.get(accountIndex).getBalance();
    }
    // get the uuid of a particular account related to the user.
    public String getAccUUID(int accountIndex) {
        return this.accounts.get(accountIndex).getUUID();
    }

    public void addAccTransaction(int accountIndex, double amount, String memo) {
        this.accounts.get(accountIndex).addTransaction(amount, memo);
    }
}
