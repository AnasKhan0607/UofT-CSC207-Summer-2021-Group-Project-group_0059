package Controller;

import Interface.UserData;
import Presenter.GuestUserNavigatorPresenter;

import java.util.Scanner;

public class GuestUserNavigatorController implements UserData {
    private String username = "Guest";

//    public GuestUserNavigatorController(){
//        username = un;
//    }

    public String currentUser(){
        return username;
    }



    public void run() {
        Scanner myObj = new Scanner(System.in);
        GuestUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* GamePlay*/

        } else if (choice == 2) {
            /* GameCreator*/
        } else if (choice == 3) {
            /*logout*/

        }
    }
}
