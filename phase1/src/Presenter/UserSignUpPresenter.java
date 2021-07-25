package Presenter;

public class UserSignUpPresenter {

    public static void display(){
        System.out.println("When creating your username, put 'Admin_' at the beginning for an Admin account, " +
                "otherwise input the username normally.");
        System.out.println("Please input your username first, press enter and then" +
                "your password, and press enter again to continue.");

    }
    public static void errorMessage(){
        System.out.println("Sorry, there is already an account with this username. " +
            "Please try again with another username and press enter.");
    }

    public static void successMessage(){
        System.out.println("Account successfully created! You can now login.");
        System.out.println("Redirecting...");
    }
}
