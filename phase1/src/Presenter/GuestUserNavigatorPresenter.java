package Presenter;

public class GuestUserNavigatorPresenter implements UserNavigatorPresenter{
    public static void display(String username){
        System.out.println("Hello, "+ username + ". what would you like to do?");
        System.out.println("1. Select a Game to create(but you cannot save as a guest user)/play");
        System.out.println("2. Logout");
        System.out.println("Type in the number to indicate your choice");
    }

    public static void redirectingMessage(){
        System.out.println("Redirecting");
    }
}
