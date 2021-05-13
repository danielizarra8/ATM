import java.util.ArrayList;

public class Account {
    private String name;
    // uuid is the identifier (id) for the account.
    private String uuid;
    // The user holder that owns this account.
    private User holder;
    // this ArrayList holds all the transactions linked to each particular account.
    private ArrayList<Transaction>transactions;

    //create constructor for the account.
    public Account(String name, User holder, Bank bank){
        //set the fields of the account in the constructor.
        this.name = name;
        this.holder = holder;
        // set the new uuid to the account.
        this.uuid = bank.getNewAccountUUID();
        // set the empty transactions.
        this.transactions = new ArrayList<Transaction>();
    }
    // return the id of the account.
    public String getUUID() {
        return this.uuid;
    }

    public String getSummaryLine() {
        //get the account's balance.
        double balance = this.getBalance();
        // format the summary depending, whether the balance is negative or not.
        if(balance >= 0){
            return String.format("%s : €%.02f : %s", this.uuid, balance, this.name);
        }else { // if negative, the amount goes in ()
            return String.format("%s : €(%.02f) : %s", this.uuid, balance, this.name);
        }
    }
    public double getBalance(){
        double balance = 0;
        // loop though the transactions and add the amount into the balance.
        for (Transaction t : this.transactions){
            balance += t.getAmount();
        }
        return balance;
    }
    // print the transaction history of the account.
    public void printTransHistory() {
        System.out.printf("\n Transaction history for account %s \n", this.uuid);
        // loop through the transactons and start with the last one.
        for (int t = this.transactions.size()-1; t>= 0; t--){
            System.out.println(this.transactions.get(t).getSummaryLine());
        }
        System.out.println();
    }
    // add a new transaction the account list.
    public void addTransaction(double amount, String memo) {
        // create a new transaction obj to add to the list of transactions.
        Transaction newTransaction = new Transaction(amount,memo,this);
        this.transactions.add(newTransaction);
    }
}
