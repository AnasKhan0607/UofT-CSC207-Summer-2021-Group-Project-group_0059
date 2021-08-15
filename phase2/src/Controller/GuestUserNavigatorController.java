package Controller;

import Interface.UserData;
import Presenter.GamePresenter;
import Presenter.GuestUserNavigatorPresenter;

import java.util.ArrayList;
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

        //Scanner myObj = new Scanner(System.in);
        while (true) {GuestUserNavigatorPresenter.display(username);
            //Integer choice = Integer.valueOf(myObj.nextLine());
            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Select a Game to create(but you cannot save as a guest user)/play");
            choices.add("2. Logout");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, "+ username + ". what would you like to do?");
            if (choice == 1) {
                /* Game*/
                GuestUserNavigatorPresenter.redirectingMessage();
                TemplateEditorController te = new TemplateEditorController();
                GameMainController gc = new GameMainController(te ,this::currentUser);
                gc.gameMenu();

            }  else if (choice == 2) {
                /*logout*/
                gamePresenter.displayTextScene(this, "CONTINUE", "Successfully logged out");
                break;

            } else {gamePresenter.displayTextScene(this, "BACK","Invalid choice, please try again");}}
    }
}
