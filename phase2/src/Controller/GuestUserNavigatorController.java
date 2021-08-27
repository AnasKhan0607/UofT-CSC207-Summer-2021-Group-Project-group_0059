package Controller;

import Interface.UserData;
import Presenter.GamePresenter;
import Presenter.GuestUserNavigatorPresenter;

import java.util.ArrayList;

/**
 * the controller class that interacts with Userinputs from GuestUser
 */
public class GuestUserNavigatorController implements UserData {
    private String username = "Guest";


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


        while (true) {

            GamePresenter gamePresenter = new GamePresenter();
            ArrayList<String> choices = new ArrayList<>();
            choices.add("1. Select a Game to create(but you cannot save as a guest user)/play");
            choices.add("2. Logout");
            int choice = 1 + gamePresenter.displayChoices(this, choices, "Hello, "+ username + ". what would you like to do?");
            if (choice == 1) {
                /* Game*/
                GuestUserNavigatorPresenter.redirectingMessage();
                TemplateEditorController te = new TemplateEditorController();
                GameMainController gameController = new GameMainController(te ,this::currentUser);
                gameController.gameMenu();

            }  else if (choice == 2) {
                /*logout*/
                gamePresenter.displayTextScene(this, "CONTINUE", "Successfully logged out");
                break;

            } else {gamePresenter.displayTextScene(this, "BACK","Invalid choice, please try again");}}
    }
}
