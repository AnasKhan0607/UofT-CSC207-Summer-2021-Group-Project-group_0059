package Presenter;

/**
 * Presenter for the login process.
 * @author Ahmad I., Ruilin P.
 */
public class UserLoginPresenter {
    /**
     * Gives the user their first instructions for logging in.
     */
    public static void display(){
        System.out.println("Please login first:");
        System.out.println("Please input your username first, press enter and then " +
                "your password, and press enter again to continue.");
        System.out.println("Don't have an account? Type Signup to register or Guest to login as a guest");

    }

    /**
     * The instructions for logging in as a guest.
     */
    public static void display2(){
        System.out.println("Dear Guest");
    }

    /**
     * Instructions for logging in after creating a new account.
     */
    public static void display3() {
        System.out.println("Please input your username first, press enter and then " +
                "your password, and press enter again to continue.");
    }

    /**
     * The error message for an incorrect login.
     */
    public static void errorMessage(){System.out.println(
            "Sorry, but either the username or the password is incorrect");}

    public static void suspensionMessage(String username){System.out.println("Suspended");
    System.out.println("(" +username + ") is currently suspended. Please contact Ruilin or Ahmad for support." );}

    public static void expiredAccountMessage() {
        System.out.println("Sorry, your temporary account has expired.");
    }
    /**
     * The message after successfully logging in.
     */
    public static void successMessage(String UserType){
        System.out.println("Logged in as " + UserType);
        System.out.println("Redirecting...");
    }
}
