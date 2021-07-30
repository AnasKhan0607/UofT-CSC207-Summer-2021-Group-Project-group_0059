package Controller;

import Interface.UserData;
import Presenter.GuestUserNavigatorPresenter;

import java.util.Scanner;
/**
 * the controller class that interacts with Userinputs from GuestUser
 */
public class GuestUserNavigatorController implements UserData {
    private String username = "Guest";

//    public GuestUserNavigatorController(){
//        username = un;
//    }
    /**
     * return the current User's username
     * @return: the username of current (Guest)User
     */
    public String currentUser(){
        return username;
    }


    /**
     * the main method that is run to accept user inputs and redirect to corresponding controllers
     */
    public void run() {
        Scanner myObj = new Scanner(System.in);
        GuestUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* Game*/
            TemplateEditorController te = new TemplateEditorController();
            GameMainController gc = new GameMainController(te ,this::currentUser);
            gc.gameMenu();

        }  else if (choice == 2) {
            /*logout*/

        }
    }
}
