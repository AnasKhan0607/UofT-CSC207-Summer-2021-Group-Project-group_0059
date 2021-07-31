package Presenter;

import Interface.UserNavigatorPresenter;

/**
 * the presenter class that display the textUI for RegularUser logged in
 */
public class RegularUserNavigatorPresenter implements UserNavigatorPresenter {
    /**
     * display the options this user is qualified to do
     * @param username the username of this user
     */
    public static void display(String username){
        System.out.println("Hello, "+ username + ". what would you like to do?");
        System.out.println("1. Select a Game to create/edit/play");
        System.out.println("2. Logout");
        System.out.println("Type in the number to indicate your choice");
    }

    /**
     * display the message that redirect is in progress
     */
    public static void redirectingMessage(){
        System.out.println("Redirecting");
    }

    /**
     * display the message that the User has successfully logged out
     */
    public static void logoutMessage(){
        System.out.println("Successfully logged out.");
    }
}
