package Controller;

import Presenter.UserLoginPresenter;

import java.util.ArrayList;
import java.util.Scanner;

public class RegularUserNavigatorController implements UserNavigatorController{
    private ArrayList<String> userInput;



    public static void run() {
        Scanner myObj = new Scanner(System.in);
        UserLoginPresenter.display();
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
