package Presenter;

public class RegularUserNavigatorPresenter implements UserNavigatorPresenter{
    public static void display(){
        System.out.println("Hello, what would you like to do?");
        System.out.println("1. Select a Game to play");
        System.out.println("2. Select a Template and create a new Game");
        System.out.println("3. See the Games you have created but made private/ and edit");
        System.out.println("4. Logout");
        System.out.println("Type in the number to indicate your choice");
    }

    public static void redirectingMessage(){
        System.out.println("Redirecting");
    }
}
