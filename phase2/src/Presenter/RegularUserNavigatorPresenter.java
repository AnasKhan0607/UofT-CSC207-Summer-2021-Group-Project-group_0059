package Presenter;

import Interface.UserNavigatorPresenter;

/**
 * the presenter class that display the textUI for RegularUser logged in
 */
public class RegularUserNavigatorPresenter implements UserNavigatorPresenter {


    /**
     * display the message that redirect is in progress
     */
    public static void redirectingMessage(){
        System.out.println("Redirecting");
        System.out.println();
    }


}
