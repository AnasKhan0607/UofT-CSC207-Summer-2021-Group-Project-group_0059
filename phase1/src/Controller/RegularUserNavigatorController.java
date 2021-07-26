package Controller;

import Entity.RegularUser;
import Presenter.RegularUserNavigatorPresenter;
import Presenter.UserLoginPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class RegularUserNavigatorController implements UserNavigatorController{
    private RegularUser currentUser;

    public RegularUserNavigatorController(RegularUser r){
        currentUser = r;
    }



    public void run() {
        Scanner myObj = new Scanner(System.in);
        RegularUserNavigatorPresenter.display(currentUser.getUsername());
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
