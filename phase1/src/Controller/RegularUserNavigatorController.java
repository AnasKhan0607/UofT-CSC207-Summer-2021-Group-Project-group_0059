package Controller;

import Interface.UserData;
import Presenter.RegularUserNavigatorPresenter;

import java.util.Scanner;
/**
 * the controller class that interacts with Userinputs from RegularUser
 */
public class RegularUserNavigatorController implements UserData {
    private String username;
    /**
     * the constructor for this controller class
     * @param un
     */
    public RegularUserNavigatorController(String un){
        username = un;
    }
    /**
     * return the current User's username
     * @return: the username of current (Regular)User
     */
    public String currentUser(){
        return username;
    }


    /**
     * the main method that is run to accept user inputs and redirect to corresponding controllers
     */
    public void run() {
        Scanner myObj = new Scanner(System.in);
        RegularUserNavigatorPresenter.display(username);
        Integer choice = Integer.valueOf(myObj.nextLine());
        if (choice == 1) {
            /* Game*/
            RegularUserNavigatorPresenter.redirectingMessage();
            TemplateEditorController te = new TemplateEditorController();
            GameMainController gc = new GameMainController(te ,this::currentUser);
            gc.gameMenu();

        } else if (choice == 2) {
            /*logout*/
            RegularUserNavigatorPresenter.logoutMessage();

        }
    }
}
