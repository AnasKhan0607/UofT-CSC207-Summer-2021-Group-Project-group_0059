package Presenter;

/**
 * Presenter for the login process.
 * @author Ahmad I., Ruilin P.
 */
public class UserLoginPresenter {



    /**
     * The message after successfully logging in.
     */
    public static void successMessage(String UserType){
        System.out.println("Logged in as " + UserType);
        System.out.println("Redirecting...");
        System.out.println();
        System.out.println();
    }


}
