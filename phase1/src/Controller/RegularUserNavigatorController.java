package Controller;

import Interface.UserData;
import Presenter.RegularUserNavigatorPresenter;

import java.util.Scanner;

public class RegularUserNavigatorController implements UserData {
    private String username;

    public RegularUserNavigatorController(String un){
        username = un;
    }

    public String currentUser(){
        return username;
    }



    public void run() {
        Scanner myObj = new Scanner(System.in);
        RegularUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* GamePlay*/

        } else if (choice == 2) {
            /* GameCreator*/
        } else if (choice == 3) {
            /* GameEditor*/
        } else if (choice == 4) {
            /*logout*/

        }
    }
}
