import java.util.ArrayList;
import java.util.Random;

public class Bank {
    private String name;
    private ArrayList<User> users;
    private ArrayList<Account>accounts;

    //constructor of the bank class.
    public Bank(String name){
        this.name = name;
        //initialize both empty lists of users and accounts.
        this.users = new ArrayList<User>();
        this.accounts = new ArrayList<Account>();
    }

    // generate the randomized uuid(identifier) for the User.
    public String getNewUserUUID() {

        String uuid ="";
        Random ran = new Random();
        // the length of the random uuid.
        int length = 6;
        boolean noUnique;

        // do while loop to ensure a new id is generated(loop until noUnique is false).
        do {
            //generate the number here.
            for (int c = 0; c <length; c++){
                // this generate each random integer digit and cast to String.
                uuid += ((Integer)ran.nextInt(10)).toString();
            }
            // confirm the uuid is indeed unique with a foreach loop which iterates though the users list.
            noUnique = false;
            for(User u: this.users){
                if(uuid.compareTo(u.getUUID()) == 0){
                    noUnique = true;
                    break;
                }
            }
        }while (noUnique);
        return uuid;
    }
    // generate the randomized uuid(identifier) for the Account.
    public String getNewAccountUUID() {
        String uuid ="";
        Random ran = new Random();
        // the length of the random uuid.
        int length = 10;
        boolean noUnique;

        // do while loop to ensure a new id is generated(loop until noUnique is false).
        do {
            //generate the number here.
            for (int c = 0; c <length; c++){
                // this generate each random integer digit and cast to String.
                uuid += ((Integer)ran.nextInt(10)).toString();
            }
            // confirm the uuid is indeed unique with a foreach loop which iterates though the users list.
            noUnique = false;
            for(Account a: this.accounts){
                if(uuid.compareTo(a.getUUID()) == 0){
                    noUnique = true;
                    break;
                }
            }
        }while (noUnique);
        return uuid;
    }
    // addAccount add the new account the accounts list
    public void addAcount(Account account){
        this.accounts.add(account);
    }

    // method to create an user and add it to the list of users.
    public User addUSer(String fName, String lName, String pin){
        User newUser = new User(fName,lName,pin,this);
        this.users.add(newUser);

        //create a saving account for the user.
        Account newAccount = new Account("Savings", newUser, this);
        //create a saving account for the user and add it to the User and Bank accounts list.
        newUser.addAccount(newAccount);
        this.accounts.add(newAccount);

        return newUser;
    }
    // create a login screen that will return a screen if the credentials are valid.
    public User userLoggin(String userID, String pin){
        //search through the list of users.
        for (User u : this.users){
            //compare each user with the user trying to log in.
            if (u.getUUID().compareTo(userID) == 0 && u.validatePin(pin)){
                return u;

            }
        }
        // if there not a valid user or pin, return null.
        return null;
    }

    public String getName() {
        return this.name;
    }
}
