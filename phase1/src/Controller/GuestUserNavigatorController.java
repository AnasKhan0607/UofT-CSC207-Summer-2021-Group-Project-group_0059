package Controller;

import Entity.GuestUser;
import Presenter.GuestUserNavigatorPresenter;
import Presenter.UserLoginPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class GuestUserNavigatorController implements UserNavigatorController{
    private String username;

    public GuestUserNavigatorController(String un){
        username = un;
    }

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
