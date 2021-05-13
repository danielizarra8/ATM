import java.util.Date;

public class Transaction {
    //The amount of the transaction.
    private double amount;
    //Date of the transaction.
    private Date timestamp;
    // A memo* of the transaction.
    private String memo;
    //The account in which the transaction was performed.
    private Account inAccount;

    //initiate the constructors by overloading it based on the arguments it will receive.
    public Transaction(double amount, Account inAccount){
        this.amount = amount;
        this.inAccount = inAccount;
        this.timestamp = new Date();
        this.memo = "";

    }
    public Transaction(double amount, String memo, Account inAccount){
        //call the two-argument constructor first by: >.
        this(amount,inAccount);
        // set the memo then.
        this.memo = memo;
    }
    // returns the amount of transactions.
    public double getAmount() {
        return this.amount;
    }
    // get a string summarize of the transaction which returns it as a string value.
    public String getSummaryLine() {
        if(this.amount >=0){
            return String.format("Date: %s : €%s0.2f : %s ",this.timestamp.toString(), this.amount, this.memo );
        } else {
            // same as above but if the amount is negative then the amount goes in ().
            return String.format("Date: %s : €(%s0.2f) : %s ",this.timestamp.toString(), -this.amount, this.memo );
        }
    }
}
