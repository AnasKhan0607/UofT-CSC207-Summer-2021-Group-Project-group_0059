package Presenter;

public class UserLoginPresenter {

    public static void display(){
        System.out.println("Please login first:");
        System.out.println("Please input your username first, press enter and then" +
                "your password, and press enter again to continue.");
        System.out.println("Don't have an account? Type Signup to register or Guest to login as a guest");

    }

    public static void display2(){
        System.out.println("input username. press enter to continue");
    }


    public static void errorMessage(){System.out.println("Sorry, but either username or password is incorrect");}

    public static void successMessage(){
        System.out.println("Logged in as Guest");
        System.out.println("Redirecting...");
    }
}
