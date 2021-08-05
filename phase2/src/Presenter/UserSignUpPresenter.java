package Presenter;

/**
 * Presenter for the signup process.
 * @author Ahmad I., Ruilin P.
 */
public class UserSignUpPresenter {
    /**
     * Initial instructions for creating an account.
     */
    public static void display(){
        System.out.println("When creating your username, put 'Admin_' at the beginning for an Admin account, " +
                "otherwise input the username normally.");
        System.out.println("Please input your username first, press enter and then" +
                "your password, and press enter again to continue.");

    }

    /**
     * Error message if a user exists with that username.
     */
    public static void errorMessage(){
        System.out.println("Sorry, there is already an account with this username. " +
            "Please try again with another username and press enter.");
    }

    /**
     * Message if an account is successfully created.
     */
    public static void successMessage(){
        System.out.println("Account successfully created! You can now login.");
        System.out.println("Redirecting...");
    }
}
